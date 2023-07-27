package ru.qwerty.schedulerbot.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The group representation in the database.
 */
@Getter
@Setter
@Entity
@Builder
@Table(name = "groups")
@NoArgsConstructor
@AllArgsConstructor
public class GroupEntity {

    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String number;
}
