package ru.qwerty.schedulerbot.core.repository.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.qwerty.schedulerbot.core.repository.UserRepository;
import ru.qwerty.schedulerbot.data.entity.UserEntity;
import ru.qwerty.schedulerbot.data.model.UserChanges;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DefaultUserRepository implements UserRepository {

    private static final String SELECT_BY_ID_QUERY
            = "SELECT id, creation_date, group_number, is_subscribed FROM users WHERE id = ?";

    private static final String SELECT_SUBSCRIBED_QUERY
            = "SELECT id, creation_date, group_number, is_subscribed FROM users";

    private static final String INSERT_QUERY
            = "INSERT INTO users (id, creation_date, is_subscribed, group_number) VALUES (?, ?, ?, ?)";

    private static final String UPDATE_QUERY = "UPDATE users SET group_number = ?, is_subscribed = ? WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public UserEntity findById(long id) {
        log.info("Get user from db: id = {}", id);

        try {
            UserEntity userEntity = jdbcTemplate.queryForObject(
                    SELECT_BY_ID_QUERY,
                    (resultSet, rowNum) -> createUserEntity(resultSet),
                    id
            );

            log.info("Get user from db: id = {} status = success", id);
            return userEntity;
        } catch (EmptyResultDataAccessException e) {
            log.warn("Get user from db: id = {} status = failed. User not found", id);
            return null;
        } catch (Exception e) {
            log.error("Get user from db: id = {} status = failed", id, e);
            throw e;
        }
    }

    @Override
    public List<UserEntity> findSubscribed(int offset, int batchSize) {
        log.info("Get subscribed users from db: offset = {}, batchSize = {}", offset, batchSize);

        try {
            List<UserEntity> userEntities = jdbcTemplate.query(
                    SELECT_SUBSCRIBED_QUERY,
                    (resultSet, rowNum) -> createUserEntity(resultSet)
            );
            log.info("Get subscribed users from db status = success: list size = {}", userEntities.size());
            return userEntities;
        } catch (Exception e) {
            log.error("Get subscribed users from db status = failed", e);
            throw e;
        }
    }

    @Override
    public void save(UserEntity user) {
        log.info("Save user to db: id = {}", user.getId());

        try {
            jdbcTemplate.update(
                    INSERT_QUERY,
                    user.getId(),
                    user.getCreationDate(),
                    user.getIsSubscribed(),
                    user.getGroupNumber()
            );
            log.info("Save user to db: id = {} status = success", user.getId());
        } catch (Exception e) {
            log.error("Save user to db: id = {} status = failed", user.getId(), e);
            throw e;
        }
    }

    @Override
    public void update(long id, UserChanges userChanges) {
        log.info("Update user in db: id = {}", id);

        try {
            jdbcTemplate.update(UPDATE_QUERY, userChanges.getGroupNumber(), userChanges.getIsSubscribed(), id);
            log.info("Update user in db: id = {} status = success", id);
        } catch (Exception e) {
            log.error("Update user in db: id = {} status = failed", id, e);
            throw e;
        }
    }

    private static UserEntity createUserEntity(ResultSet resultSet) throws SQLException {
        return UserEntity.builder()
                .id(resultSet.getLong("id"))
                .creationDate(resultSet.getDate("creation_date"))
                .groupNumber(resultSet.getString("group_number"))
                .isSubscribed(resultSet.getBoolean("is_subscribed"))
                .build();
    }
}
