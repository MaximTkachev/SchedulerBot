package ru.qwerty.schedulerbot.handler.implement;

import ru.qwerty.schedulerbot.data.model.Command;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.handler.Handler;

/**
 * The handler is used for the case when a user wants to get a list of available bot commands.
 */
public class GetMenuHandler implements Handler {

    private static final String MESSAGE = Command.GET_CURRENT_GROUP + " получить вашу группу по умолчанию\n" +
            Command.GET_SCHEDULE + " получить расписание вашей группы на сегодня\n" +
            Command.SET_GROUP + " уставновить вашу группу по умолчанию\n" +
            Command.SUBSCRIBE + " подписаться на ежедневную рассылку расписания\n" +
            Command.UNSUBSCRIBE + " отписаться от ежедневной рассылки расписания";

    @Override
    public String handle(Message message) {
        return MESSAGE;
    }
}
