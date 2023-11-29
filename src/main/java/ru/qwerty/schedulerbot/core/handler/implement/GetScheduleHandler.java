package ru.qwerty.schedulerbot.core.handler.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.qwerty.schedulerbot.core.handler.Handler;
import ru.qwerty.schedulerbot.core.service.GroupService;
import ru.qwerty.schedulerbot.core.service.ScheduleService;
import ru.qwerty.schedulerbot.core.service.UserService;
import ru.qwerty.schedulerbot.data.model.Command;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.i18n.MessageFactory;
import ru.qwerty.schedulerbot.i18n.MessageKey;

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

    private final GroupService groupService;

    private final ScheduleService scheduleService;

    private final Clock clock;

    @Override
    public Command getCommand() {
        return Command.GET_SCHEDULE;
    }

    @Override
    public String handle(Message message) {
        String groupNumber = userService.get(message.getChatId()).getGroupNumber();
        if (groupNumber == null) {
            return MessageFactory.createMessage(
                    message.getLanguage(),
                    MessageKey.GROUP_NOT_SET_ERROR,
                    Command.SET_GROUP
            );
        }

        String groupId = groupService.get(groupNumber).getId();

        return MessageFactory.createSchedule(
                message.getLanguage(),
                scheduleService.get(groupId, new Date(clock.millis()))
        );
    }
}
