package ru.qwerty.schedulerbot.core.validation;

import ru.qwerty.schedulerbot.exception.InvalidArgumentException;
import ru.qwerty.schedulerbot.i18n.MessageKey;

import java.util.Objects;

public final class Validator {

    private Validator() {
    }

    public static void checkGroupNumber(String groupNumber) {
        if (Objects.isNull(groupNumber)) {
            throw new InvalidArgumentException(MessageKey.GROUP_NUMBER_NOT_SPECIFIED);
        }

        if (groupNumber.length() != 6) {
            throw new InvalidArgumentException(MessageKey.INVALID_GROUP_NUMBER);
        }

        if (!groupNumber.startsWith("97")) {
            throw new InvalidArgumentException(MessageKey.FACULTY_IS_NOT_SUPPORTED);
        }
    }
}
