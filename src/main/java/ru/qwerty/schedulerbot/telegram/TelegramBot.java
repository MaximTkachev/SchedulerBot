package ru.qwerty.schedulerbot.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.qwerty.schedulerbot.config.property.BotProperties;
import ru.qwerty.schedulerbot.core.handler.HandlerFactory;
import ru.qwerty.schedulerbot.core.util.SerializationUtils;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.exception.ServiceException;
import ru.qwerty.schedulerbot.i18n.Language;
import ru.qwerty.schedulerbot.i18n.MessageFactory;
import ru.qwerty.schedulerbot.i18n.MessageKey;

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
            BotProperties properties,
            MessageSender messageSender,
            HandlerFactory handlerFactory,
            ThreadPoolExecutor threadPoolExecutor
    ) {
        this.botUsername = properties.getUsername();
        this.botToken = properties.getToken();
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
        log.info("Received message = {}", SerializationUtils.serialize(update));

        threadPoolExecutor.submit(() -> {
            log.info(
                    "Message handling started: chat id = {} pool size = {} queue size = {}",
                    update.getMessage().getChat().getId(),
                    threadPoolExecutor.getPoolSize(),
                    threadPoolExecutor.getQueue().size()
            );

            messageSender.send(update.getMessage().getChatId(), handleMessage(update));
        });
    }

    private String handleMessage(Update update) {
        Language language = createLanguage(update.getMessage().getFrom().getLanguageCode());
        try {
            Message message = new Message(
                    update.getMessage().getChat().getId(),
                    update.getMessage().getText(),
                    language
            );

            return handlerFactory.create(message).handle(message);
        } catch (ServiceException e) {
            return MessageFactory.createMessage(language, e.getMessageKey());
        } catch (Exception e) {
            log.error("Unexpected internal error", e);
            return MessageFactory.createMessage(language, MessageKey.INTERNAL_SERVER_ERROR);
        }
    }

    private Language createLanguage(String languageCode) {
        Language language = Language.fromString(languageCode);
        return language != null ? language : Language.getDefault();
    }
}
