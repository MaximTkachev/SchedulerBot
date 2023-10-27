package ru.qwerty.schedulerbot.core.service;

import ru.qwerty.schedulerbot.data.entity.UserEntity;
import ru.qwerty.schedulerbot.data.model.UserChanges;

import java.util.List;

/**
 * The service provides user management functionality.
 */
public interface UserService {

    UserEntity get(long id);

    List<UserEntity> getSubscribed(int offset, int batchSize);

    void save(UserEntity userEntity);

    void update(long id, UserChanges userChanges);
}
