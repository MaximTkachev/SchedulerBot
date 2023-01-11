package ru.qwerty.schedulerbot.exception;

import ru.qwerty.schedulerbot.model.Response;

public class StrangeServerDataException extends InternalException {

    public StrangeServerDataException() {
        super(Response.STRANGE_SERVER_DATA);
    }
}
