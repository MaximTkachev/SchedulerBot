package ru.qwerty.schedulerbot.core.handler.implement;

import io.micrometer.core.instrument.Counter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.qwerty.schedulerbot.core.handler.AbstractHandler;
import ru.qwerty.schedulerbot.core.metric.PrometheusMetricService;
import ru.qwerty.schedulerbot.core.service.UserService;
import ru.qwerty.schedulerbot.data.converter.UserMapper;
import ru.qwerty.schedulerbot.data.model.Command;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.data.prometheus.PrometheusMetricNames;
import ru.qwerty.schedulerbot.i18n.MessageFactory;
import ru.qwerty.schedulerbot.i18n.MessageKey;

/**
 * The handler is used to register user when they send the first message.
 */
@Slf4j
@Component
public class StartHandler extends AbstractHandler {

    private final UserMapper userMapper;

    private final Counter userCounter;

    public StartHandler(UserMapper userMapper, UserService userService, PrometheusMetricService metricService) {
        super(userService);
        this.userMapper = userMapper;
        this.userCounter = metricService.get(PrometheusMetricNames.USER_COUNTER);
    }

    @Override
    public Command getCommand() {
        return Command.START;
    }

    @Override
    public String doHandle(Message message) {
        userService.save(userMapper.map(message));
        userCounter.increment();
        return MessageFactory.createMessage(message.getLanguage(), MessageKey.START_RESPONSE, Command.GET_MENU);
    }
}
