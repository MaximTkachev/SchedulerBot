package ru.qwerty.schedulerbot.exception;

import ru.qwerty.schedulerbot.data.model.Response;

public class InvalidArgumentException extends InternalException {

    public InvalidArgumentException(Response response) {
        super(response);
    }
}
