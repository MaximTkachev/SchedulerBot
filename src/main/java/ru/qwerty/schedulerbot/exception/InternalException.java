package ru.qwerty.schedulerbot.exception;

import ru.qwerty.schedulerbot.model.Response;

/**
 * The exception is the parent class for exceptions whose text can be returned to the user as a response.
 */
public abstract class InternalException extends RuntimeException {

    protected InternalException(Response response) {
        super(response.getMessage());
    }
}
