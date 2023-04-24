package ru.qwerty.schedulerbot.handler.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.qwerty.schedulerbot.core.service.GroupService;
import ru.qwerty.schedulerbot.core.service.UserService;
import ru.qwerty.schedulerbot.data.entity.GroupEntity;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.data.model.UserChanges;
import ru.qwerty.schedulerbot.handler.Handler;

/**
 * The handler is used for the case when a user wants to set his default group.
 */
@Slf4j
@RequiredArgsConstructor
public class SetGroupHandler implements Handler {

    private static final String SUCCESSFUL_RESULT_MESSAGE = "Группа по умолчанию успешно изменена";

    private final GroupService groupService;

    private final UserService userService;

    @Override
    public String handle(Message message) {
        String groupNumber = getGroupFromMessage(message.getText());
        GroupEntity group = groupService.getByNumber(groupNumber);

        UserChanges userChanges = new UserChanges();
        userChanges.setGroup(group);

        userService.update(message.getId(), userChanges);
        return SUCCESSFUL_RESULT_MESSAGE;
    }

    private static String getGroupFromMessage(String message) {
        try {
            return message.split(" ")[1];
        } catch (Exception e) {
            return null;
        }
    }
}
