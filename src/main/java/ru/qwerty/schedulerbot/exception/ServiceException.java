package ru.qwerty.schedulerbot.exception;

import ru.qwerty.schedulerbot.data.model.Response;

/**
 * The exception is the parent class for exceptions whose text can be returned to the user as a response.
 */
public abstract class ServiceException extends RuntimeException {

    protected ServiceException(Response response) {
        super(response.getMessage());
    }
}
