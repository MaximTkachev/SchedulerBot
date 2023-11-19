package ru.qwerty.schedulerbot.handler.implement;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;
import ru.qwerty.schedulerbot.data.model.Command;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.data.prometheus.PrometheusCounterNames;
import ru.qwerty.schedulerbot.handler.Handler;
import ru.qwerty.schedulerbot.i18n.MessageFactory;
import ru.qwerty.schedulerbot.i18n.MessageKey;

/**
 * The handler is used for the case when a user wants to get a list of available bot commands.
 */
@Component
public class GetMenuHandler implements Handler {

    private final Counter counter;

    public GetMenuHandler(MeterRegistry meterRegistry) {
        this.counter = meterRegistry.counter(PrometheusCounterNames.GET_MENU_COUNTER);
    }

    @Override
    public String handle(Message message) {
        counter.increment();

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
