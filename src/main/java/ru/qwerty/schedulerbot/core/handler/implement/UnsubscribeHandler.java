package ru.qwerty.schedulerbot.core.handler.implement;

import org.springframework.stereotype.Component;
import ru.qwerty.schedulerbot.core.handler.AbstractHandler;
import ru.qwerty.schedulerbot.core.service.UserService;
import ru.qwerty.schedulerbot.data.model.Command;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.data.model.UserChanges;
import ru.qwerty.schedulerbot.i18n.MessageFactory;
import ru.qwerty.schedulerbot.i18n.MessageKey;

/**
 * The handler is used for the case when a user wants to unsubscribe from the daily schedule mailing.
 */
@Component
public class UnsubscribeHandler extends AbstractHandler {

    public UnsubscribeHandler(UserService userService) {
        super(userService);
    }

    @Override
    public Command getCommand() {
        return Command.UNSUBSCRIBE;
    }

    @Override
    public String doHandle(Message message) {
        UserChanges userChanges = UserChanges.builder()
                .isSubscribed(false)
                .build();
        userService.update(message.getChatId(), userChanges);

        return MessageFactory.createMessage(message.getLanguage(), MessageKey.UNSUBSCRIBE_RESPONSE);
    }
}
