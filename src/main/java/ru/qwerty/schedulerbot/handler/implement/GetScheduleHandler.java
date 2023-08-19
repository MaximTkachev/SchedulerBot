package ru.qwerty.schedulerbot.handler.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.qwerty.schedulerbot.core.service.ScheduleService;
import ru.qwerty.schedulerbot.core.service.UserService;
import ru.qwerty.schedulerbot.data.entity.GroupEntity;
import ru.qwerty.schedulerbot.data.model.Command;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.handler.Handler;
import ru.qwerty.schedulerbot.message.MessageFactory;
import ru.qwerty.schedulerbot.message.MessageKey;

import java.time.Clock;
import java.util.Date;

/**
 * The handler is used for the case when a user wants to get a schedule for the current day.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GetScheduleHandler implements Handler {

    private final UserService userService;

    private final ScheduleService scheduleService;

    private final Clock clock;

    @Override
    public String handle(Message message) {
        GroupEntity group = userService.get(message.getId()).getGroup();
        if (group == null) {
            return MessageFactory.createMessage(
                    message.getLanguage(),
                    MessageKey.GROUP_NOT_SET_ERROR,
                    Command.SET_GROUP
            );
        }

        return MessageFactory.createSchedule(
                message.getLanguage(),
                scheduleService.get(group.getId(), new Date(clock.millis()))
        );
    }
}
