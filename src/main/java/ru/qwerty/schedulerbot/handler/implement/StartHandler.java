package ru.qwerty.schedulerbot.handler.implement;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.qwerty.schedulerbot.handler.Handler;

/**
 * The handler is used to register user when they send the first message.
 */
public class StartHandler implements Handler {

    @Override
    public String handle(Update update) {
        return "Вы хотите зарегистрироваться";
    }
}
