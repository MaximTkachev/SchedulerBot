package ru.qwerty.schedulerbot.util;

import ru.qwerty.schedulerbot.exception.InvalidArgumentException;
import ru.qwerty.schedulerbot.model.Response;

import java.util.Objects;

public class Validator {

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
