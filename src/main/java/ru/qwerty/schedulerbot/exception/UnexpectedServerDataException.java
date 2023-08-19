package ru.qwerty.schedulerbot.exception;

import ru.qwerty.schedulerbot.message.MessageKey;

public class UnexpectedServerDataException extends ServiceException {

    public UnexpectedServerDataException() {
        super(MessageKey.UNEXPECTED_SERVER_DATA);
    }
}
