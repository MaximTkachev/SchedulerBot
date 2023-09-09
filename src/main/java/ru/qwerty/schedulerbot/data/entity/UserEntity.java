package ru.qwerty.schedulerbot.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * User representation in the database.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private Long id;

    private Date creationDate;

    private String groupNumber;

    private Boolean isSubscribed;
}
