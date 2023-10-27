package ru.qwerty.schedulerbot.core.service.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.qwerty.schedulerbot.core.repository.UserRepository;
import ru.qwerty.schedulerbot.core.service.GroupService;
import ru.qwerty.schedulerbot.core.service.UserService;
import ru.qwerty.schedulerbot.data.entity.UserEntity;
import ru.qwerty.schedulerbot.data.model.UserChanges;
import ru.qwerty.schedulerbot.exception.DuplicateDataException;
import ru.qwerty.schedulerbot.exception.ObjectNotFoundException;
import ru.qwerty.schedulerbot.message.MessageKey;

import java.util.List;

/**
 * The default implementation of the {@link UserService} interface.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    private final GroupService groupService;

    @Override
    @Transactional(readOnly = true)
    public UserEntity get(long id) {
        log.info("Get user: id = {}", id);

        UserEntity user = userRepository.findById(id);
        if (user == null) {
            throw new ObjectNotFoundException(MessageKey.USER_NOT_FOUND);
        }

        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserEntity> getSubscribed(int offset, int batchSize) {
        log.info("Get subscribed users offset = {} batchSize = {}", offset, batchSize);
        if (offset < 0 || batchSize <= 0) {
            return List.of();
        }

        return userRepository.findSubscribed(offset, batchSize);
    }

    @Override
    @Transactional
    public void save(UserEntity userEntity) {
        log.info("Save user = {}", userEntity);

        UserEntity user = userRepository.findById(userEntity.getId());
        if (user != null) {
            throw new DuplicateDataException(MessageKey.USER_WAS_ALREADY_REGISTERED);
        }

        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void update(long id, UserChanges userChanges) {
        log.info("Update user: id = {}, changes = {}", id, userChanges);

        UserEntity user = get(id);
        if (userChanges.getGroupNumber() == null) {
            userChanges.setGroupNumber(user.getGroupNumber());
        } else {
            groupService.get(userChanges.getGroupNumber());
        }

        if (userChanges.getIsSubscribed() == null) {
            userChanges.setIsSubscribed(user.getIsSubscribed());
        }

        userRepository.update(id, userChanges);
    }
}
