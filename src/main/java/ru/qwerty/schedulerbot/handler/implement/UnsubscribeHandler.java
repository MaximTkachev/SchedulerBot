package ru.qwerty.schedulerbot.handler.implement;

import lombok.RequiredArgsConstructor;
import ru.qwerty.schedulerbot.core.service.UserService;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.data.model.UserChanges;
import ru.qwerty.schedulerbot.handler.Handler;

/**
 * The handler is used for the case when a user wants to unsubscribe from the daily schedule mailing.
 */
@RequiredArgsConstructor
public class UnsubscribeHandler implements Handler {

    private static final String SUCCESSFUL_RESULT_MESSAGE = "Вы успешно отписались от ежедневной рассылки расписания";

    private final UserService userService;

    @Override
    public String handle(Message message) {
        UserChanges userChanges = new UserChanges();
        userChanges.setIsSubscribed(false);
        userService.update(message.getId(), userChanges);

        return SUCCESSFUL_RESULT_MESSAGE;
    }
}
