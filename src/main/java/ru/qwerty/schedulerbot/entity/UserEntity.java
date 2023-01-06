package ru.qwerty.schedulerbot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * User representation in the database.
 */
@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private GroupEntity group;
}
