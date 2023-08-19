package ru.qwerty.schedulerbot.data.model;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import ru.qwerty.schedulerbot.data.entity.GroupEntity;
import ru.qwerty.schedulerbot.data.entity.UserEntity;

/**
 * The class contains changes to edit instance of the {@link UserEntity} class.
 */
@Value
@ToString
@Builder
public class UserChanges {

    GroupEntity group;

    Boolean isSubscribed;
}
