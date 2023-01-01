package ru.qwerty.schedulerbot.telegram;

/**
 * The component is used to send messages to users.
 */
public interface MessageSender {

    /**
     * Sends a message to a chat.
     *
     * @param chatId  The identifier of the chat to which the message will be sent.
     * @param message The message text.
     */
    void send(long chatId, String message);
}
