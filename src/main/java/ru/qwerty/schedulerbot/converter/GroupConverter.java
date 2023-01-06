package ru.qwerty.schedulerbot.converter;

import ru.qwerty.schedulerbot.entity.GroupEntity;
import ru.qwerty.schedulerbot.model.dto.Group;

/**
 * The interface is used to convert instances of classes associated with groups.
 */
public interface GroupConverter {

    GroupEntity convertToEntity(Group group);
}
