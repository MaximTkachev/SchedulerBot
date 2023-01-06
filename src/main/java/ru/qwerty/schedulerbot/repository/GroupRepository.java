package ru.qwerty.schedulerbot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.qwerty.schedulerbot.entity.GroupEntity;

import java.util.Optional;

@Repository
public interface GroupRepository extends CrudRepository<GroupEntity, String> {

    Optional<GroupEntity> findByNumber(String number);
}
