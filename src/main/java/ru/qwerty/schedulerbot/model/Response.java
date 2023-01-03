package ru.qwerty.schedulerbot.model;

import lombok.Getter;

/**
 * The enum contains a list of responses to the user.
 */
public enum Response {

    USER_WAS_ALREADY_REGISTERED("Вы уже зарегистрированы");

    @Getter
    private final String message;

    Response(String message) {
        this.message = message;
    }
}
