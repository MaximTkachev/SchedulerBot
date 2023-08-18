package ru.qwerty.schedulerbot.handler.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.qwerty.schedulerbot.core.service.UserService;
import ru.qwerty.schedulerbot.data.converter.UserMapper;
import ru.qwerty.schedulerbot.data.model.Command;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.handler.Handler;
import ru.qwerty.schedulerbot.message.MessageFactory;
import ru.qwerty.schedulerbot.message.MessageKey;

/**
 * The handler is used to register user when they send the first message.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StartHandler implements Handler {

    private final UserMapper userMapper;

    private final UserService userService;

    @Override
    public String handle(Message message) {
        userService.save(userMapper.map(message));
        return MessageFactory.createMessage(message.getLanguage(), MessageKey.START_RESPONSE, Command.GET_MENU);
    }
}
