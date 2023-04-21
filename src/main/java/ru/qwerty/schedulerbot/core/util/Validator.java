package ru.qwerty.schedulerbot.core.util;

import ru.qwerty.schedulerbot.data.model.Response;
import ru.qwerty.schedulerbot.exception.InvalidArgumentException;

import java.util.Objects;

public final class Validator {

    private Validator() {
    }

    public static void checkGroupNumber(String groupNumber) {
        if (Objects.isNull(groupNumber)) {
            throw new InvalidArgumentException(Response.GROUP_NUMBER_NOT_SPECIFIED);
        }

        if (groupNumber.length() != 6) {
            throw new InvalidArgumentException(Response.INVALID_GROUP_NUMBER);
        }

        if (!groupNumber.startsWith("97")) {
            throw new InvalidArgumentException(Response.FACULTY_IS_NOT_SUPPORTED);
        }
    }
}
