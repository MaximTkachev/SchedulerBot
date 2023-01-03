package ru.qwerty.schedulerbot.handler.implement;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.qwerty.schedulerbot.handler.Handler;
import ru.qwerty.schedulerbot.model.Command;

/**
 * The handler is used for the case when a user wants to get a list of available bot commands.
 */
public class GetMenuHandler implements Handler {

    @Override
    public String handle(Update update) {
        return
                Command.GET_CURRENT_GROUP + " получить вашу группу по умолчанию\n" +
                Command.GET_SCHEDULE + " получить расписание вашей группы на сегодня\n" +
                Command.SET_GROUP + " уставновить вашу группу по умолчанию\n" +
                Command.SUBSCRIBE + " подписаться на ежедневную рассылку расписания\n" +
                Command.UNSUBSCRIBE + " отписаться от ежедневной рассылки расписания";
    }
}
