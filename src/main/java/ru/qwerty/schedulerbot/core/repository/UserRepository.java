package ru.qwerty.schedulerbot.core.repository;

import ru.qwerty.schedulerbot.data.entity.UserEntity;
import ru.qwerty.schedulerbot.data.model.UserChanges;

public interface UserRepository {

    UserEntity findById(long id);

    void save(UserEntity user);

    void update(long id, UserChanges userChanges);
}
