package ru.qwerty.schedulerbot.exception;

import ru.qwerty.schedulerbot.data.model.Response;

public class ActionNotAllowedException extends ServiceException {

    public ActionNotAllowedException(Response response) {
        super(response);
    }
}
