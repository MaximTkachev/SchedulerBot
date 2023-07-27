package ru.qwerty.schedulerbot.handler.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.qwerty.schedulerbot.core.service.UserService;
import ru.qwerty.schedulerbot.data.converter.UserConverter;
import ru.qwerty.schedulerbot.data.model.Command;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.handler.Handler;

/**
 * The handler is used to register user when they send the first message.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StartHandler implements Handler {

    private static final String SUCCESSFUL_RESULT_MESSAGE
            = "Добро пожаловать!\nВведите " + Command.GET_MENU + " чтобы получить список доступных команд";

    private final UserConverter userConverter;

    private final UserService userService;

    @Override
    public String handle(Message message) {
        userService.save(userConverter.map(message));
        log.info("User was registered from user message; {}", message);
        return SUCCESSFUL_RESULT_MESSAGE;
    }
}
