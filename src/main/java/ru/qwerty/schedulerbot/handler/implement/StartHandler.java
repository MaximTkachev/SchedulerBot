package ru.qwerty.schedulerbot.handler.implement;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.qwerty.schedulerbot.core.service.UserService;
import ru.qwerty.schedulerbot.data.converter.UserMapper;
import ru.qwerty.schedulerbot.data.model.Command;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.data.prometheus.PrometheusCounterNames;
import ru.qwerty.schedulerbot.handler.Handler;
import ru.qwerty.schedulerbot.i18n.MessageFactory;
import ru.qwerty.schedulerbot.i18n.MessageKey;

/**
 * The handler is used to register user when they send the first message.
 */
@Slf4j
@Component
public class StartHandler implements Handler {

    private final UserMapper userMapper;

    private final UserService userService;

    private final Counter counter;

    public StartHandler(UserMapper userMapper, UserService userService, MeterRegistry meterRegistry) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.counter = meterRegistry.counter(PrometheusCounterNames.USER_COUNTER);
    }

    @Override
    public Command getCommand() {
        return Command.START;
    }

    @Override
    public String handle(Message message) {
        userService.save(userMapper.map(message));
        counter.increment();
        return MessageFactory.createMessage(message.getLanguage(), MessageKey.START_RESPONSE, Command.GET_MENU);
    }
}
