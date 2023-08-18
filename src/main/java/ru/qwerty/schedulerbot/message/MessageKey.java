package ru.qwerty.schedulerbot.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageKey {
    START_RESPONSE("startResponse"),
    MENU_RESPONSE("menuResponse"),
    GET_GROUP_RESPONSE("getGroupResponse"),
    SET_GROUP_RESPONSE("setGroupResponse"),
    SUBSCRIBE_RESPONSE("subscribeResponse"),
    UNSUBSCRIBE_RESPONSE("unsubscribeResponse"),
    UNKNOWN_COMMAND_RESPONSE("unknownCommandResponse"),
    GROUP_NOT_SET_ERROR("groupNotSetError"),
    USER_WAS_ALREADY_REGISTERED("userWasAlreadyRegistered"),
    GROUP_NUMBER_NOT_SPECIFIED("groupNumberNotSpecified"),
    INVALID_GROUP_NUMBER("invalidGroupNumber"),
    FACULTY_IS_NOT_SUPPORTED("facultyIsNotSupported"),
    GROUP_NOT_FOUND("groupNotFound"),
    USER_NOT_FOUND("userNotFound"),
    UNEXPECTED_SERVER_DATA("unexpectedServerData"),
    INTERNAL_SERVER_ERROR("internalServerError"),
    EMPTY_SCHEDULE_TEMPLATE("emptyScheduleTemplate"),
    TITLE_TEMPLATE("titleTemplate");

    private final String value;
}
