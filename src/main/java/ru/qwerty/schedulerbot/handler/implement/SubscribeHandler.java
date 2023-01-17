package ru.qwerty.schedulerbot.handler.implement;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.qwerty.schedulerbot.handler.Handler;
import ru.qwerty.schedulerbot.model.UserChanges;
import ru.qwerty.schedulerbot.service.UserService;

/**
 * The handler is used for the case when a user wants to subscribe to a daily schedule mailing.
 */
@RequiredArgsConstructor
public class SubscribeHandler implements Handler {

    private final UserService userService;

    @Override
    public String handle(Update update) {
        UserChanges userChanges = new UserChanges();
        userChanges.setIsSubscribed(true);
        userService.update(update.getMessage().getChat().getId(), userChanges);

        return "Вы успешно подписались на ежедневную рассылку расписания";
    }
}
