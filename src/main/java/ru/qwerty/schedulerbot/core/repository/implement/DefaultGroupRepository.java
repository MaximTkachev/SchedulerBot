package ru.qwerty.schedulerbot.core.repository.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.qwerty.schedulerbot.core.repository.GroupRepository;
import ru.qwerty.schedulerbot.data.entity.GroupEntity;

import java.sql.PreparedStatement;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DefaultGroupRepository implements GroupRepository {

    private static final String SELECT_BY_NUMBER_QUERY = "SELECT id, number FROM groups WHERE number = ?";

    private static final String BATCH_INSERT = "INSERT INTO groups (id, number) VALUES (?, ?)";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public GroupEntity findByNumber(String number) {
        log.info("Get group from db: number = {}", number);

        try {
            GroupEntity groupEntity = jdbcTemplate.queryForObject(
                    SELECT_BY_NUMBER_QUERY,
                    (resultSet, rowNum) -> new GroupEntity(resultSet.getString("id"), resultSet.getString("number")),
                    number
            );

            log.info("Get group from db: number = {} status = success", number);
            return groupEntity;
        } catch (EmptyResultDataAccessException e) {
            log.warn("Get group from db: number = {} status = failed. Group not found", number);
            return null;
        } catch (Exception e) {
            log.error("Get group from db: number = {} status = failed", number, e);
            throw e;
        }
    }

    @Override
    public void saveAll(List<GroupEntity> groups) {
        log.info("Save groups to db: list size = {}", groups.size());

        try {
            jdbcTemplate.batchUpdate(
                    BATCH_INSERT,
                    groups,
                    groups.size(),
                    (PreparedStatement ps, GroupEntity group) -> {
                        ps.setString(1, group.getId());
                        ps.setString(2, group.getNumber());
                    }
            );
            log.info("Save groups to db: list size = {} status = success", groups.size());
        } catch (Exception e) {
            log.error("Save groups to db: list size = {} status = failed", groups.size(), e);
            throw e;
        }
    }
}
