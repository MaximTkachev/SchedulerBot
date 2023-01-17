package ru.qwerty.schedulerbot.handler.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.qwerty.schedulerbot.entity.GroupEntity;
import ru.qwerty.schedulerbot.handler.Handler;
import ru.qwerty.schedulerbot.model.UserChanges;
import ru.qwerty.schedulerbot.service.GroupService;
import ru.qwerty.schedulerbot.service.UserService;
import ru.qwerty.schedulerbot.util.Validator;

/**
 * The handler is used for the case when a user wants to set his default group.
 */
@Slf4j
@RequiredArgsConstructor
public class SetGroupHandler implements Handler {

    private final GroupService groupService;

    private final UserService userService;

    @Override
    public String handle(Update update) {
        String groupNumber = getGroupFromMessage(update.getMessage().getText());
        Validator.checkGroupNumber(groupNumber);
        GroupEntity group = groupService.getByNumber(groupNumber);

        UserChanges userChanges = new UserChanges();
        userChanges.setGroup(group);
        userService.update(update.getMessage().getChat().getId(), userChanges);

        return "Группа по умолчанию успешно изменена";
    }

    private static String getGroupFromMessage(String message) {
        try {
            return message.split(" ")[1];
        } catch (Exception e) {
            return null;
        }
    }
}
