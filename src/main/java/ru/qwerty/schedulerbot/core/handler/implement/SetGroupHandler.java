package ru.qwerty.schedulerbot.core.handler.implement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.qwerty.schedulerbot.core.handler.AbstractHandler;
import ru.qwerty.schedulerbot.core.service.UserService;
import ru.qwerty.schedulerbot.data.model.Command;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.data.model.UserChanges;
import ru.qwerty.schedulerbot.i18n.MessageFactory;
import ru.qwerty.schedulerbot.i18n.MessageKey;

/**
 * The handler is used for the case when a user wants to set his default group.
 */
@Slf4j
@Component
public class SetGroupHandler extends AbstractHandler {

    public SetGroupHandler(UserService userService) {
        super(userService);
    }

    @Override
    public Command getCommand() {
        return Command.SET_GROUP;
    }

    @Override
    public String doHandle(Message message) {
        UserChanges userChanges = UserChanges.builder()
                .groupNumber(getGroupFromMessage(message.getText()))
                .build();
        userService.update(message.getChatId(), userChanges);

        return MessageFactory.createMessage(message.getLanguage(), MessageKey.SET_GROUP_RESPONSE);
    }

    private static String getGroupFromMessage(String message) {
        try {
            return message.split(" ")[1];
        } catch (Exception e) {
            return null;
        }
    }
}
