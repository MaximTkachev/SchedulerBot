package ru.qwerty.schedulerbot.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.qwerty.schedulerbot.converter.UserConverter;
import ru.qwerty.schedulerbot.handler.implement.DefaultHandler;
import ru.qwerty.schedulerbot.handler.implement.ErrorHandler;
import ru.qwerty.schedulerbot.handler.implement.GetCurrentGroupHandler;
import ru.qwerty.schedulerbot.handler.implement.GetMenuHandler;
import ru.qwerty.schedulerbot.handler.implement.GetScheduleHandler;
import ru.qwerty.schedulerbot.handler.implement.SetGroupHandler;
import ru.qwerty.schedulerbot.handler.implement.StartHandler;
import ru.qwerty.schedulerbot.handler.implement.SubscribeHandler;
import ru.qwerty.schedulerbot.handler.implement.UnsubscribeHandler;
import ru.qwerty.schedulerbot.model.Command;
import ru.qwerty.schedulerbot.service.GroupService;
import ru.qwerty.schedulerbot.service.ScheduleService;
import ru.qwerty.schedulerbot.service.UserService;

import java.time.Clock;

/**
 * This component is used to create a user command handler.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class HandlerFactory {

    private final UserConverter userConverter;

    private final UserService userService;

    private final GroupService groupService;

    private final ScheduleService scheduleService;

    private final Clock clock;

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
                return new GetCurrentGroupHandler(userService);
            case GET_MENU:
                return new GetMenuHandler();
            case GET_SCHEDULE:
                return new GetScheduleHandler(userService, scheduleService, clock);
            case SET_GROUP:
                return new SetGroupHandler(groupService, userService);
            case START:
                return new StartHandler(userConverter, userService);
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
