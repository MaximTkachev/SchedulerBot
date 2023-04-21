package ru.qwerty.schedulerbot.handler.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.qwerty.schedulerbot.core.service.ScheduleService;
import ru.qwerty.schedulerbot.core.service.UserService;
import ru.qwerty.schedulerbot.data.entity.GroupEntity;
import ru.qwerty.schedulerbot.data.model.Command;
import ru.qwerty.schedulerbot.handler.Handler;

import java.time.Clock;
import java.util.Date;

/**
 * The handler is used for the case when a user wants to get a schedule for the current day.
 */
@Slf4j
@RequiredArgsConstructor
public class GetScheduleHandler implements Handler {

    private final UserService userService;

    private final ScheduleService scheduleService;

    private final Clock clock;

    @Override
    public String handle(Update update) {
        GroupEntity group = userService.getById(update.getMessage().getChat().getId()).getGroup();
        if (group == null) {
            return "Вы не задали свой номер группы.\nСделайте это, используя команду " + Command.SET_GROUP;
        }

        return scheduleService.get(group.getId(), new Date(clock.millis()));
    }
}
