package ru.qwerty.schedulerbot.exception;

import ru.qwerty.schedulerbot.message.MessageKey;

public class ObjectNotFoundException extends ServiceException {

    public ObjectNotFoundException(MessageKey messageKey) {
        super(messageKey);
    }
}
