package ru.qwerty.schedulerbot.data.model;

import lombok.Getter;

/**
 * The enum contains a list of user commands.
 */
@Getter
public enum Command {
    GET_GROUP("/gg"),
    GET_MENU("/m"),
    GET_SCHEDULE("/s"),
    SET_GROUP("/sg"),
    START("/start"),
    SUBSCRIBE("/sub"),
    UNSUBSCRIBE("/unsub");

    private final String value;

    Command(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static Command fromString(String value) {
        for (Command command : Command.values()) {
            if (command.value.equals(value)) {
                return command;
            }
        }

        return null;
    }
}
