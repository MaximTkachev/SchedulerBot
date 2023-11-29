package ru.qwerty.schedulerbot.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import ru.qwerty.schedulerbot.data.model.Command;
import ru.qwerty.schedulerbot.data.model.Message;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This component is used to create a user command handler.
 */
@Slf4j
@Component
public class HandlerFactory {

    private final Map<Command, Handler> handlers;

    public HandlerFactory(List<Handler> handlers) {
        this.handlers = handlers.stream()
                .collect(Collectors.toMap(Handler::getCommand, Function.identity()));
    }

    public Handler create(Message message) {
        if (Strings.isEmpty(message.getText())) {
            return handlers.get(Command.UNKNOWN);
        }

        Command command = Command.fromString(message.getText().split(" ")[0]);
        if (command == null) {
            log.warn("Failed to get command from user message: {}", message);
            return handlers.get(Command.UNKNOWN);
        }

        return handlers.get(command);
    }
}
