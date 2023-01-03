package ru.qwerty.schedulerbot.exception;

import ru.qwerty.schedulerbot.model.Response;

/**
 * The exception is thrown when a repository tries to save data to the DB that is already contained there.
 */
public class DuplicateDataException extends InternalException {

    public DuplicateDataException(Response response) {
        super(response);
    }
}
