package ru.qwerty.schedulerbot.data.entity;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

import java.util.Date;

/**
 * User representation in the database.
 */
@Value
@Builder
@ToString
public class UserEntity {

    Long id;

    Date creationDate;

    String groupNumber;

    Boolean isSubscribed;
}
