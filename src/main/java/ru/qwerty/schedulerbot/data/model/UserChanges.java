package ru.qwerty.schedulerbot.data.model;

import lombok.Data;
import ru.qwerty.schedulerbot.data.entity.GroupEntity;
import ru.qwerty.schedulerbot.data.entity.UserEntity;

/**
 * The class contains changes to edit instance of the {@link UserEntity} class.
 */
@Data
public class UserChanges {

    private GroupEntity group;

    private Boolean isSubscribed;
}
