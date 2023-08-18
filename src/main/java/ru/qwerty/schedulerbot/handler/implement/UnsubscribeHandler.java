package ru.qwerty.schedulerbot.handler.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.qwerty.schedulerbot.core.service.UserService;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.data.model.UserChanges;
import ru.qwerty.schedulerbot.handler.Handler;
import ru.qwerty.schedulerbot.message.MessageFactory;
import ru.qwerty.schedulerbot.message.MessageKey;

/**
 * The handler is used for the case when a user wants to unsubscribe from the daily schedule mailing.
 */
@Component
@RequiredArgsConstructor
public class UnsubscribeHandler implements Handler {

    private final UserService userService;

    @Override
    public String handle(Message message) {
        UserChanges userChanges = UserChanges.builder()
                .isSubscribed(false)
                .build();
        userService.update(message.getId(), userChanges);

        return MessageFactory.createMessage(message.getLanguage(), MessageKey.UNSUBSCRIBE_RESPONSE);
    }
}
