package ru.qwerty.schedulerbot.core.handler;

import lombok.RequiredArgsConstructor;
import ru.qwerty.schedulerbot.core.service.UserService;
import ru.qwerty.schedulerbot.data.entity.UserEntity;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.data.model.UserChanges;

@RequiredArgsConstructor
public abstract class AbstractHandler implements Handler {

    protected final UserService userService;

    @Override
    public String handle(Message message) {
        try {
            return doHandle(message);
        } finally {
            UserEntity user = userService.get(message.getChatId());
            if (user.getLanguage() != message.getLanguage()) {
                UserChanges userChanges = UserChanges.builder()
                        .language(message.getLanguage())
                        .build();

                userService.update(user.getId(), userChanges);
            }
        }
    }

    protected abstract String doHandle(Message message);
}
