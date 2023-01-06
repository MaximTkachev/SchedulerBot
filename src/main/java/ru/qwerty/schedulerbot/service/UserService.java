package ru.qwerty.schedulerbot.service;

import ru.qwerty.schedulerbot.entity.UserEntity;

/**
 * The service provides user management functionality.
 */
public interface UserService {

    void save(UserEntity user);

    void update(UserEntity newData, long id);
}
