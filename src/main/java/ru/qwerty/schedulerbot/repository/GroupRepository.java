package ru.qwerty.schedulerbot.repository;

import org.springframework.data.repository.CrudRepository;
import ru.qwerty.schedulerbot.entity.GroupEntity;

public interface GroupRepository extends CrudRepository<GroupEntity, String> {
}
