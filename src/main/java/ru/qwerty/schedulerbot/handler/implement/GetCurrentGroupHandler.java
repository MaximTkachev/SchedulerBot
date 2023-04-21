package ru.qwerty.schedulerbot.handler.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.qwerty.schedulerbot.core.service.UserService;
import ru.qwerty.schedulerbot.data.entity.GroupEntity;
import ru.qwerty.schedulerbot.data.entity.UserEntity;
import ru.qwerty.schedulerbot.handler.Handler;

/**
 * The handler is used for the case when a user wants to get his default group.
 */
@Slf4j
@RequiredArgsConstructor
public class GetCurrentGroupHandler implements Handler {

    private static final String BASIC_MESSAGE = "Ваша группа по умолчанию: ";

    private final UserService userService;

    @Override
    public String handle(Update update) {
        UserEntity userEntity = userService.getById(update.getMessage().getChat().getId());
        GroupEntity group = userEntity.getGroup();
        if (group == null) {
            return "Вы пока не задали группу по умолчанию";
        }
        return BASIC_MESSAGE + group.getNumber();
    }
}
