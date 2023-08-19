package ru.qwerty.schedulerbot.data.converter;

import ru.qwerty.schedulerbot.data.entity.GroupEntity;
import ru.qwerty.schedulerbot.data.model.dto.Group;

/**
 * The interface is used to convert instances of classes associated with groups.
 */
public interface GroupMapper {

    GroupEntity map(Group group);
}
