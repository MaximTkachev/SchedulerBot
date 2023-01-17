package ru.qwerty.schedulerbot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.qwerty.schedulerbot.config.BotConfig;
import ru.qwerty.schedulerbot.exception.InternalException;
import ru.qwerty.schedulerbot.handler.Handler;
import ru.qwerty.schedulerbot.handler.HandlerFactory;
import ru.qwerty.schedulerbot.mapper.UpdateMapper;
import ru.qwerty.schedulerbot.telegram.MessageSender;

import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;

    private final MessageSender messageSender;

    private final HandlerFactory handlerFactory;

    private final ThreadPoolExecutor threadPoolExecutor;

    @Override
    public String getBotUsername() {
        return config.getUsername();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("Message was received: {}", UpdateMapper.mapForLog(update));
        threadPoolExecutor.submit(() -> {
            log.info(
                    "Message was added to pool. Chat id: {}. Current pool size: {}. Current queue size: {}",
                    update.getMessage().getChat().getId(),
                    threadPoolExecutor.getPoolSize(),
                    threadPoolExecutor.getQueue().size()
            );
            String message = handleMessage(update);
            long chatId = update.getMessage().getChatId();
            messageSender.send(chatId, message);
        });
    }

    private String handleMessage(Update update) {
        try {
            Handler handler = handlerFactory.create(update);
            return handler.handle(update);
        } catch (InternalException e) {
            log.warn("Failed to handle user message from update: {}", UpdateMapper.mapForLog(update), e);
            return e.getMessage();
        } catch (Exception e) {
            log.error("Failed to handle user message from update: {}", UpdateMapper.mapForLog(update), e);
            return "Произошла непредвиденная ошибка на сервере";
        }
    }
}
