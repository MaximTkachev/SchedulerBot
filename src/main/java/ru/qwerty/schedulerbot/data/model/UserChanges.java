package ru.qwerty.schedulerbot.data.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import ru.qwerty.schedulerbot.data.entity.UserEntity;

/**
 * The class contains changes to edit instance of the {@link UserEntity} class.
 */
@Data
@ToString
@Builder
public class UserChanges {

    String groupNumber;

    Boolean isSubscribed;
}
