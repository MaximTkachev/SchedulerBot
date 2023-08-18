package ru.qwerty.schedulerbot.message;

import lombok.Getter;

import java.util.Locale;

@Getter
public enum Language {
    RU("ru"),
    EN("en");

    private final String code;

    private final Locale locale;

    Language(String code) {
        this.code = code;
        this.locale = new Locale(code);
    }

    public static Language fromString(String value) {
        for (Language language : values()) {
            if (language.code.equals(value)) {
                return language;
            }
        }

        return null;
    }

    public static Language getDefault() {
        return EN;
    }
}
