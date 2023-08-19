package ru.qwerty.schedulerbot.handler.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.qwerty.schedulerbot.core.service.UserService;
import ru.qwerty.schedulerbot.data.entity.GroupEntity;
import ru.qwerty.schedulerbot.data.model.Command;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.handler.Handler;
import ru.qwerty.schedulerbot.message.MessageFactory;
import ru.qwerty.schedulerbot.message.MessageKey;

/**
 * The handler is used for the case when a user wants to get his default group.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GetGroupHandler implements Handler {

    private final UserService userService;

    @Override
    public String handle(Message message) {
        GroupEntity group = userService.get(message.getId()).getGroup();
        if (group == null) {
            return MessageFactory.createMessage(
                    message.getLanguage(),
                    MessageKey.GROUP_NOT_SET_ERROR,
                    Command.SET_GROUP
            );
        }

        return MessageFactory.createMessage(message.getLanguage(), MessageKey.GET_GROUP_RESPONSE, group.getNumber());
    }
}
