package ru.qwerty.schedulerbot.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.qwerty.schedulerbot.data.model.Command;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.handler.implement.UnknownCommandHandler;
import ru.qwerty.schedulerbot.handler.implement.GetCurrentGroupHandler;
import ru.qwerty.schedulerbot.handler.implement.GetMenuHandler;
import ru.qwerty.schedulerbot.handler.implement.GetScheduleHandler;
import ru.qwerty.schedulerbot.handler.implement.SetGroupHandler;
import ru.qwerty.schedulerbot.handler.implement.StartHandler;
import ru.qwerty.schedulerbot.handler.implement.SubscribeHandler;
import ru.qwerty.schedulerbot.handler.implement.UnsubscribeHandler;

/**
 * This component is used to create a user command handler.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class HandlerFactory {

    private final GetCurrentGroupHandler getCurrentGroupHandler;

    private final GetMenuHandler getMenuHandler;

    private final GetScheduleHandler getScheduleHandler;

    private final SetGroupHandler setGroupHandler;

    private final StartHandler startHandler;

    private final SubscribeHandler subscribeHandler;

    private final UnknownCommandHandler unknownCommandHandler;

    private final UnsubscribeHandler unsubscribeHandler;

    public Handler create(Message message) {
        if (message.getText() == null || message.getText().isEmpty()) {
            return unknownCommandHandler;
        }

        Command command = Command.fromString(message.getText().split(" ")[0]);
        if (command == null) {
            log.warn("Failed to get command from user message: {}", message);
            return unknownCommandHandler;
        }

        switch (command) {
            case GET_CURRENT_GROUP:
                return getCurrentGroupHandler;
            case GET_MENU:
                return getMenuHandler;
            case GET_SCHEDULE:
                return getScheduleHandler;
            case SET_GROUP:
                return setGroupHandler;
            case START:
                return startHandler;
            case SUBSCRIBE:
                return subscribeHandler;
            case UNSUBSCRIBE:
                return unsubscribeHandler;
            default:
                log.error("Failed to create handler from user message: {}. Command: {}", message, command);
                return unknownCommandHandler;
        }
    }
}
