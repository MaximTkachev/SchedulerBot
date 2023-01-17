package ru.qwerty.schedulerbot.service;

import ru.qwerty.schedulerbot.entity.UserEntity;
import ru.qwerty.schedulerbot.model.UserChanges;

/**
 * The service provides user management functionality.
 */
public interface UserService {

    UserEntity getById(long id);

    void save(UserEntity user);

    void update(long id, UserChanges userChanges);
}
