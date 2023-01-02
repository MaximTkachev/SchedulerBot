package ru.qwerty.schedulerbot.handler;

import lombok.Getter;

public enum Command {

    GET_CURRENT_GROUP("/get-group"),
    GET_MENU("/menu"),
    GET_SCHEDULE("/get"),
    SET_GROUP("/set-group"),
    START("/start"),
    SUBSCRIBE("/subscribe"),
    UNSUBSCRIBE("/unsubscribe");

    @Getter
    private final String value;

    Command(String value){
        this.value = value;
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
