package ru.qwerty.schedulerbot.handler.implement;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.qwerty.schedulerbot.handler.Handler;

/**
 * The handler is used for the case when the Telegram sent invalid data.
 */
public class ErrorHandler implements Handler {

    @Override
    public String handle(Update update) {
        return "Получено некорректное сообщение";
    }
}
