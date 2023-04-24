package ru.qwerty.schedulerbot.handler;

import ru.qwerty.schedulerbot.data.model.Message;

/**
 * The interface is used to handle user commands.
 */
public interface Handler {

    /**
     * Handles a user's command.
     *
     * @param message Data received from the user.
     * @return The text to be sent to the user.
     */
    String handle(Message message);
}
