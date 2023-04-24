package ru.qwerty.schedulerbot.exception;

import ru.qwerty.schedulerbot.data.model.Response;

/**
 * The exception is thrown when a repository tries to save data to the DB that is already contained there.
 */
public class DuplicateDataException extends ServiceException {

    public DuplicateDataException(Response response) {
        super(response);
    }
}
