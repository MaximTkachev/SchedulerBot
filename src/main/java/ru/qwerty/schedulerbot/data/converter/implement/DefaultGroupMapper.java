package ru.qwerty.schedulerbot.data.converter.implement;

import org.springframework.stereotype.Component;
import ru.qwerty.schedulerbot.data.converter.GroupMapper;
import ru.qwerty.schedulerbot.data.entity.GroupEntity;
import ru.qwerty.schedulerbot.data.model.dto.Group;

/**
 * The default implementation of the {@link GroupMapper} interface.
 */
@Component
public class DefaultGroupMapper implements GroupMapper {

    @Override
    public GroupEntity map(Group group) {
        return GroupEntity.builder()
                .id(group.getId())
                .number(group.getName())
                .build();
    }
}
