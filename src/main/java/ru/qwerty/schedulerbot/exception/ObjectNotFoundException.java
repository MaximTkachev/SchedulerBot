package ru.qwerty.schedulerbot.exception;

import ru.qwerty.schedulerbot.data.model.Response;

public class ObjectNotFoundException extends InternalException {

    public ObjectNotFoundException(Response response) {
        super(response);
    }
}
