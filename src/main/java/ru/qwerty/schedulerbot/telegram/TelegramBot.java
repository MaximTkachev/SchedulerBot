package ru.qwerty.schedulerbot.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.qwerty.schedulerbot.config.property.BotProperties;
import ru.qwerty.schedulerbot.core.util.Mapper;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.exception.ServiceException;
import ru.qwerty.schedulerbot.handler.HandlerFactory;

import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Service
class TelegramBot extends TelegramLongPollingBot {

    private final String botUsername;

    private final String botToken;

    private final MessageSender messageSender;

    private final HandlerFactory handlerFactory;

    private final ThreadPoolExecutor threadPoolExecutor;

    TelegramBot(
            BotProperties config,
            MessageSender messageSender,
            HandlerFactory handlerFactory,
            ThreadPoolExecutor threadPoolExecutor
    ) {
        this.botUsername = config.getUsername();
        this.botToken = config.getToken();
        this.messageSender = messageSender;
        this.handlerFactory = handlerFactory;
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("Received message = {}", Mapper.serialize(update));

        threadPoolExecutor.submit(() -> {
            log.info(
                    "Message handling started: chat id = {} pool size = {} queue size = {}",
                    update.getMessage().getChat().getId(),
                    threadPoolExecutor.getPoolSize(),
                    threadPoolExecutor.getQueue().size()
            );

            String message = handleMessage(update);
            messageSender.send(update.getMessage().getChatId(), message);
        });
    }

    private String handleMessage(Update update) {
        try {
            Message message = new Message(update.getMessage().getChat().getId(), update.getMessage().getText());
            return handlerFactory.create(message).handle(message);
        } catch (ServiceException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "Произошла непредвиденная ошибка на сервере";
        }
    }
}
