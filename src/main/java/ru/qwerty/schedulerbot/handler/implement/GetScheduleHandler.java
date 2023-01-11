package ru.qwerty.schedulerbot.handler.implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.qwerty.schedulerbot.api.RequestManager;
import ru.qwerty.schedulerbot.entity.GroupEntity;
import ru.qwerty.schedulerbot.exception.StrangeServerDataException;
import ru.qwerty.schedulerbot.handler.Handler;
import ru.qwerty.schedulerbot.mapper.Mapper;
import ru.qwerty.schedulerbot.model.Command;
import ru.qwerty.schedulerbot.model.dto.DaySchedule;
import ru.qwerty.schedulerbot.service.UserService;

import java.time.Clock;
import java.util.Date;

/**
 * The handler is used for the case when a user wants to get a schedule for the current day.
 */
@Slf4j
@RequiredArgsConstructor
public class GetScheduleHandler implements Handler {

    private final UserService userService;

    private final RequestManager requestManager;

    private final Clock clock;

    @Override
    public String handle(Update update) {
        GroupEntity group = userService.getById(update.getMessage().getChat().getId()).getGroup();
        if (group == null) {
            return "Вы не задали свой номер группы.\nСделайте это, используя команду " + Command.SET_GROUP;
        }

        DaySchedule schedule = fetchSchedule(group);
        return Mapper.serializeDaySchedule(schedule);
    }

    private DaySchedule fetchSchedule(GroupEntity group) {
        try {
            return requestManager.fetchSchedule(group.getId(), new Date(clock.millis()));
        } catch (JsonProcessingException e) {
            throw new StrangeServerDataException();
        }
    }
}
