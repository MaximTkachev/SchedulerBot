package ru.qwerty.schedulerbot.exception;

public class ActionNotAllowedException extends RuntimeException {

    public ActionNotAllowedException(String message) {
        super(message);
    }
}
