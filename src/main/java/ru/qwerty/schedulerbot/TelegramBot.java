package ru.qwerty.schedulerbot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.qwerty.schedulerbot.config.BotConfig;
import ru.qwerty.schedulerbot.handler.Handler;
import ru.qwerty.schedulerbot.handler.HandlerFactory;
import ru.qwerty.schedulerbot.telegram.MessageSender;

@Service
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;

    private final MessageSender messageSender;

    private final HandlerFactory handlerFactory;

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
        Handler handler = handlerFactory.create(update);
        String message = handler.handle(update);

        long chatId = update.getMessage().getChatId();
        messageSender.send(chatId, message);
    }
}
