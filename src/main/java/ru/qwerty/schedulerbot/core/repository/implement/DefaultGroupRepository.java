package ru.qwerty.schedulerbot.core.repository.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
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
        try {
            GroupEntity groupEntity = jdbcTemplate.queryForObject(
                    SELECT_BY_NUMBER_QUERY,
                    (resultSet, rowNum) -> new GroupEntity(resultSet.getString("id"), resultSet.getString("number")),
                    number
            );

            log.info("get group successfully");
            return groupEntity;
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (IncorrectResultSizeDataAccessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to get group");
            throw e;
        }
    }

    @Override
    public void saveAll(List<GroupEntity> groups) {
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
            log.info("Successfully saved/updated {} groups", groups.size());
        } catch (Exception e) {
            log.error("Failed to save/update groups", e);
            throw e;
        }
    }
}
