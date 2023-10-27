package ru.qwerty.schedulerbot.core.repository;

import ru.qwerty.schedulerbot.data.entity.UserEntity;
import ru.qwerty.schedulerbot.data.model.UserChanges;

import java.util.List;

public interface UserRepository {

    UserEntity findById(long id);

    List<UserEntity> findSubscribed(int offset, int batchSize);

    void save(UserEntity user);

    void update(long id, UserChanges userChanges);
}
