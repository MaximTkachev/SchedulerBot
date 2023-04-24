package ru.qwerty.schedulerbot.exception;

import ru.qwerty.schedulerbot.data.model.Response;

public class InvalidArgumentException extends ServiceException {

    public InvalidArgumentException(Response response) {
        super(response);
    }
}
