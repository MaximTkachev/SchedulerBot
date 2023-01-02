package ru.qwerty.schedulerbot.handler.implement;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.qwerty.schedulerbot.handler.Handler;

/**
 * The handler is used for the case when a user wants to unsubscribe from the daily schedule mailing.
 */
public class UnsubscribeHandler implements Handler {

    @Override
    public String handle(Update update) {
        return "Вы хотите отписаться от рассылки расписания";
    }
}
