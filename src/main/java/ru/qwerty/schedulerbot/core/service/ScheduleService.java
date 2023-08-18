package ru.qwerty.schedulerbot.core.service;

import ru.qwerty.schedulerbot.data.model.dto.Schedule;

import java.util.Date;

/**
 * The service provides schedule management functionality.
 */
public interface ScheduleService {

    Schedule get(String groupId, Date date);
}
