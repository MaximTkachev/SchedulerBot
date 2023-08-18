package ru.qwerty.schedulerbot.exception;

import ru.qwerty.schedulerbot.message.MessageKey;

public class InvalidArgumentException extends ServiceException {

    public InvalidArgumentException(MessageKey messageKey) {
        super(messageKey);
    }
}
