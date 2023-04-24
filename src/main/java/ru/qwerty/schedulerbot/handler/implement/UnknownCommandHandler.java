package ru.qwerty.schedulerbot.handler.implement;

import ru.qwerty.schedulerbot.data.model.Command;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.handler.Handler;

/**
 * The handler is used for the case when a user sent an invalid command.
 */
public class UnknownCommandHandler implements Handler {

    private static final String MESSAGE
            = "Неизвестная команда.\nПолучите список доступных команд, используя команду " + Command.GET_MENU;

    @Override
    public String handle(Message message) {
        return MESSAGE;
    }
}
