package ru.qwerty.schedulerbot.core.service;

import ru.qwerty.schedulerbot.data.entity.UserEntity;
import ru.qwerty.schedulerbot.data.model.UserChanges;

/**
 * The service provides user management functionality.
 */
public interface UserService {

    UserEntity getById(long id);

    void save(UserEntity user);

    void update(long id, UserChanges userChanges);
}
