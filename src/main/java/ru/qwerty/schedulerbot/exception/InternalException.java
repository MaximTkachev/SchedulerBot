package ru.qwerty.schedulerbot.exception;

import ru.qwerty.schedulerbot.i18n.MessageKey;

public class InternalException extends ServiceException {

    public InternalException() {
        super(MessageKey.INTERNAL_SERVER_ERROR);
    }
}
