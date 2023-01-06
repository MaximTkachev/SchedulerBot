package ru.qwerty.schedulerbot.model;

import lombok.Getter;

/**
 * The enum contains a list of responses to the user.
 */
public enum Response {

    USER_WAS_ALREADY_REGISTERED("Вы уже зарегистрированы"),

    GROUP_NUMBER_NOT_SPECIFIED("Не задан номер группы"),

    INVALID_GROUP_NUMBER("Некорректный номер группы"),

    FACULTY_IS_NOT_SUPPORTED("Некорректный номер группы или факультет не поддерживается"),

    GROUP_NOT_FOUND("Группа не найдена"),

    USER_NOT_FOUND("Не можем найти вас в БД");

    @Getter
    private final String message;

    Response(String message) {
        this.message = message;
    }
}
