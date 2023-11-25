package ru.qwerty.schedulerbot.core.handler;

import ru.qwerty.schedulerbot.data.model.Command;
import ru.qwerty.schedulerbot.data.model.Message;

/**
 * The interface is used to handle user commands.
 */
public interface Handler {

    Command getCommand();

    String handle(Message message);
}
