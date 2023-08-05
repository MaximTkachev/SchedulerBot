package ru.qwerty.schedulerbot.core.api;

import ru.qwerty.schedulerbot.data.model.dto.DaySchedule;
import ru.qwerty.schedulerbot.data.model.dto.Group;

import java.util.Date;
import java.util.List;

/**
 * The interface is used to send http requests to external services.
 */
public interface RequestManager {

    List<Group> fetchGroups();

    DaySchedule fetchSchedule(String groupId, Date date);
}
