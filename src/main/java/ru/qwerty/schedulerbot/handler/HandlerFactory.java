package ru.qwerty.schedulerbot.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.qwerty.schedulerbot.handler.implement.DefaultHandler;
import ru.qwerty.schedulerbot.handler.implement.ErrorHandler;
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
public class HandlerFactory {

    public Handler create(Update update) {
        if (!(update.hasMessage() && update.getMessage().hasText())) {
            return new ErrorHandler();
        }
        String message = update.getMessage().getText();

        if (message.startsWith(" ")) {
            log.warn("Failed to process user message: {}", message);
            return new DefaultHandler();
        }

        Command command = Command.fromString(message.split(" ")[0]);
        if (command == null) {
            log.warn("Failed to get command from user message: {}", message);
            return new DefaultHandler();
        }

        switch (command) {
            case GET_CURRENT_GROUP:
                return new GetCurrentGroupHandler();
            case GET_MENU:
                return new GetMenuHandler();
            case GET_SCHEDULE:
                return new GetScheduleHandler();
            case SET_GROUP:
                return new SetGroupHandler();
            case START:
                return new StartHandler();
            case SUBSCRIBE:
                return new SubscribeHandler();
            case UNSUBSCRIBE:
                return new UnsubscribeHandler();
            default:
                log.error("Failed to create handler from user message: {}. Command: {}", message, command);
                return new DefaultHandler();
        }
    }
}
