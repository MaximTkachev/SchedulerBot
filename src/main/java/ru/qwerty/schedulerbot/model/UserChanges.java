package ru.qwerty.schedulerbot.model;

import lombok.Data;
import ru.qwerty.schedulerbot.entity.GroupEntity;
import ru.qwerty.schedulerbot.entity.UserEntity;

/**
 * The class contains changes to edit instance of the {@link UserEntity} class.
 */
@Data
public class UserChanges {

    private GroupEntity group;

    private Boolean isSubscribed;
}
