package ru.qwerty.schedulerbot.core.service;

import ru.qwerty.schedulerbot.data.entity.GroupEntity;

/**
 * The service provides group management functionality.
 */
public interface GroupService {

    GroupEntity getByNumber(String number);
}
