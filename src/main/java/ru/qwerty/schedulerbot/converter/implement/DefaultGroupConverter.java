package ru.qwerty.schedulerbot.converter.implement;

import org.springframework.stereotype.Component;
import ru.qwerty.schedulerbot.converter.GroupConverter;
import ru.qwerty.schedulerbot.entity.GroupEntity;
import ru.qwerty.schedulerbot.model.dto.Group;

/**
 * The default implementation of the {@link GroupConverter} interface.
 */
@Component
public class DefaultGroupConverter implements GroupConverter {

    @Override
    public GroupEntity convertToEntity(Group group) {
        GroupEntity entity = new GroupEntity();
        entity.setId(group.getId());
        entity.setNumber(group.getName());

        return entity;
    }
}
