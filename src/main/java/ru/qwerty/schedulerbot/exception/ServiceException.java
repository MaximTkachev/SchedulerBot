package ru.qwerty.schedulerbot.exception;

import lombok.Getter;
import ru.qwerty.schedulerbot.message.MessageKey;

/**
 * The exception is the parent class for exceptions whose text can be returned to the user as a response.
 */
@Getter
public abstract class ServiceException extends RuntimeException {

    private final MessageKey messageKey;

    protected ServiceException(MessageKey messageKey) {
        super(messageKey.getValue());
        this.messageKey = messageKey;
    }
}
