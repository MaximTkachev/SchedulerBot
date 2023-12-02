package ru.qwerty.schedulerbot.core.handler.implement;

import io.micrometer.core.instrument.Counter;
import org.springframework.stereotype.Component;
import ru.qwerty.schedulerbot.core.handler.AbstractHandler;
import ru.qwerty.schedulerbot.core.metric.PrometheusMetricService;
import ru.qwerty.schedulerbot.core.service.UserService;
import ru.qwerty.schedulerbot.data.model.Command;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.data.prometheus.PrometheusMetricNames;
import ru.qwerty.schedulerbot.i18n.MessageFactory;
import ru.qwerty.schedulerbot.i18n.MessageKey;

/**
 * The handler is used for the case when a user wants to get a list of available bot commands.
 */
@Component
public class GetMenuHandler extends AbstractHandler {

    private final Counter commandCallsCounter;

    public GetMenuHandler(UserService userService, PrometheusMetricService metricService) {
        super(userService);
        this.commandCallsCounter = metricService.get(PrometheusMetricNames.GET_MENU_COMMAND_CALLS_COUNTER);
    }

    @Override
    public Command getCommand() {
        return Command.GET_MENU;
    }

    @Override
    public String doHandle(Message message) {
        commandCallsCounter.increment();

        return MessageFactory.createMessage(
                message.getLanguage(),
                MessageKey.MENU_RESPONSE,
                Command.GET_GROUP,
                Command.GET_SCHEDULE,
                Command.SET_GROUP,
                Command.SUBSCRIBE,
                Command.UNSUBSCRIBE
        );
    }
}
