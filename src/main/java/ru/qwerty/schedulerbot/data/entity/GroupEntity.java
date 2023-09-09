package ru.qwerty.schedulerbot.data.entity;

import lombok.Builder;
import lombok.Value;

/**
 * The group representation in the database.
 */
@Value
@Builder
public class GroupEntity {

    String id;

    String number;
}
