package ru.qwerty.schedulerbot.exception;

import ru.qwerty.schedulerbot.data.model.Response;

public class InternalException extends ServiceException {

    public InternalException() {
        super(Response.INTERNAL_SERVER_ERROR);
    }

    public InternalException(Response response) {
        super(response);
    }
}
