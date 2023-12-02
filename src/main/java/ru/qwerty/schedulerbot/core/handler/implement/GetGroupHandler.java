package ru.qwerty.schedulerbot.core.handler.implement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.qwerty.schedulerbot.core.handler.AbstractHandler;
import ru.qwerty.schedulerbot.core.service.UserService;
import ru.qwerty.schedulerbot.data.model.Command;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.i18n.MessageFactory;
import ru.qwerty.schedulerbot.i18n.MessageKey;

/**
 * The handler is used for the case when a user wants to get his default group.
 */
@Slf4j
@Component
public class GetGroupHandler extends AbstractHandler {

    public GetGroupHandler(UserService userService) {
        super(userService);
    }

    @Override
    public Command getCommand() {
        return Command.GET_GROUP;
    }

    @Override
    public String doHandle(Message message) {
        String groupNumber = userService.get(message.getChatId()).getGroupNumber();
        if (groupNumber == null) {
            return MessageFactory.createMessage(
                    message.getLanguage(),
                    MessageKey.GROUP_NOT_SET_ERROR,
                    Command.SET_GROUP
            );
        }

        return MessageFactory.createMessage(message.getLanguage(), MessageKey.GET_GROUP_RESPONSE, groupNumber);
    }
}
