package ru.qwerty.schedulerbot.handler.implement;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.qwerty.schedulerbot.handler.Handler;

/**
 * The handler is used for the case when a user sent an invalid command.
 */
public class DefaultHandler implements Handler {

    @Override
    public String handle(Update update) {
        return "Обработчик по умолчанию";
    }
}
