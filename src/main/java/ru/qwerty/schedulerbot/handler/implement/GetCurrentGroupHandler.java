package ru.qwerty.schedulerbot.handler.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.qwerty.schedulerbot.entity.GroupEntity;
import ru.qwerty.schedulerbot.entity.UserEntity;
import ru.qwerty.schedulerbot.exception.InternalException;
import ru.qwerty.schedulerbot.handler.Handler;
import ru.qwerty.schedulerbot.mapper.UpdateMapper;
import ru.qwerty.schedulerbot.service.UserService;

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
        try {
            UserEntity userEntity = userService.getById(update.getMessage().getChat().getId());
            GroupEntity group = userEntity.getGroup();
            if (group == null) {
                return "Вы пока не задали группу по умолчанию";
            }
            return BASIC_MESSAGE + userEntity.getGroup().getNumber();
        } catch (InternalException e) {
            log.error("Failed to handle user message from update: {}", UpdateMapper.mapForLog(update), e);
            return e.getMessage();
        }
    }
}
