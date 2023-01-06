package ru.qwerty.schedulerbot.service;

import ru.qwerty.schedulerbot.entity.UserEntity;

/**
 * The service provides user management functionality.
 */
public interface UserService {

    UserEntity getById(long id);

    void save(UserEntity user);

    void update(UserEntity newData, long id);
}
