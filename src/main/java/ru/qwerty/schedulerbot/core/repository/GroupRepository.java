package ru.qwerty.schedulerbot.core.repository;

import ru.qwerty.schedulerbot.data.entity.GroupEntity;

import java.util.List;

public interface GroupRepository {

    GroupEntity findByNumber(String number);

    void saveAll(List<GroupEntity> groups);
}
