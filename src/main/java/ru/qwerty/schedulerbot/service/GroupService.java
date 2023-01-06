package ru.qwerty.schedulerbot.service;

import ru.qwerty.schedulerbot.entity.GroupEntity;

/**
 * The service provides group management functionality.
 */
public interface GroupService {

    GroupEntity getByNumber(String number);
}
