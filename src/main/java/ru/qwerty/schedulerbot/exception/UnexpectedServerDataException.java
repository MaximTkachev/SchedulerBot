package ru.qwerty.schedulerbot.exception;

import ru.qwerty.schedulerbot.data.model.Response;

public class UnexpectedServerDataException extends ServiceException {

    public UnexpectedServerDataException() {
        super(Response.UNEXPECTED_SERVER_DATA);
    }
}
