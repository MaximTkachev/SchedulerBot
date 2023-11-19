package ru.qwerty.schedulerbot.handler.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.qwerty.schedulerbot.core.service.UserService;
import ru.qwerty.schedulerbot.data.model.Command;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.data.model.UserChanges;
import ru.qwerty.schedulerbot.handler.Handler;
import ru.qwerty.schedulerbot.i18n.MessageFactory;
import ru.qwerty.schedulerbot.i18n.MessageKey;

/**
 * The handler is used for the case when a user wants to subscribe to a daily schedule mailing.
 */
@Component
@RequiredArgsConstructor
public class SubscribeHandler implements Handler {

    private final UserService userService;

    @Override
    public Command getCommand() {
        return Command.SUBSCRIBE;
    }

    @Override
    public String handle(Message message) {
        UserChanges userChanges = UserChanges.builder()
                .isSubscribed(true)
                .build();
        userService.update(message.getChatId(), userChanges);

        return MessageFactory.createMessage(message.getLanguage(), MessageKey.SUBSCRIBE_RESPONSE);
    }
}
