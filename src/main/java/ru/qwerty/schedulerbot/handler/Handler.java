package ru.qwerty.schedulerbot.handler;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * The interface is used to handle user commands.
 */
public interface Handler {

    /**
     * Handles the user's command.
     *
     * @param update Data received from the user.
     * @return The text to be sent to the user.
     */
    String handle(Update update);
}
