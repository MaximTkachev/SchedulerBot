package ru.qwerty.schedulerbot.service;

import java.util.Date;

/**
 * The service provides schedule management functionality.
 */
public interface ScheduleService {

    String get(String groupId, Date date);
}
