package ru.qwerty.schedulerbot.core.service.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.qwerty.schedulerbot.core.repository.UserRepository;
import ru.qwerty.schedulerbot.core.service.UserService;
import ru.qwerty.schedulerbot.data.entity.UserEntity;
import ru.qwerty.schedulerbot.data.model.UserChanges;
import ru.qwerty.schedulerbot.exception.DuplicateDataException;
import ru.qwerty.schedulerbot.exception.ObjectNotFoundException;
import ru.qwerty.schedulerbot.message.MessageKey;

/**
 * The default implementation of the {@link UserService} interface.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserEntity get(long id) {
        log.info("Get user: id = {}", id);
        return userRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(MessageKey.USER_NOT_FOUND)
        );
    }

    @Override
    @Transactional
    public void save(UserEntity user) {
        log.info("Save user: id = {}", user.getId());
        if (userRepository.findById(user.getId()).isPresent()) {
            throw new DuplicateDataException(MessageKey.USER_WAS_ALREADY_REGISTERED);
        }

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(long id, UserChanges userChanges) {
        log.info("Update user: id = {}, changes = {}", id, userChanges);
        UserEntity user = get(id);

        if (userChanges.getGroup() != null) {
            user.setGroup(userChanges.getGroup());
        }
        if (userChanges.getIsSubscribed() != null) {
            user.setIsSubscribed(userChanges.getIsSubscribed());
        }
    }
}
