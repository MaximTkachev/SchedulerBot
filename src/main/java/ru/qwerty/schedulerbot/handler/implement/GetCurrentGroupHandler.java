package ru.qwerty.schedulerbot.handler.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.qwerty.schedulerbot.core.service.UserService;
import ru.qwerty.schedulerbot.data.entity.GroupEntity;
import ru.qwerty.schedulerbot.data.entity.UserEntity;
import ru.qwerty.schedulerbot.data.model.Command;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.handler.Handler;

/**
 * The handler is used for the case when a user wants to get his default group.
 */
@Slf4j
@RequiredArgsConstructor
public class GetCurrentGroupHandler implements Handler {

    private static final String GROUP_NOT_SET_MESSAGE
            = "Вы не задали свой номер группы.\nСделайте это, используя команду " + Command.SET_GROUP;

    private static final String SUCCESSFUL_RESULT_MESSAGE = "Ваша группа по умолчанию: ";

    private final UserService userService;

    @Override
    public String handle(Message message) {
        UserEntity userEntity = userService.getById(message.getId());
        GroupEntity group = userEntity.getGroup();
        if (group == null) {
            return GROUP_NOT_SET_MESSAGE;
        }
        return SUCCESSFUL_RESULT_MESSAGE + group.getNumber();
    }
}
