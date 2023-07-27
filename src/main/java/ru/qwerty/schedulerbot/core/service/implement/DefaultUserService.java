package ru.qwerty.schedulerbot.core.service.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.qwerty.schedulerbot.core.repository.UserRepository;
import ru.qwerty.schedulerbot.core.service.UserService;
import ru.qwerty.schedulerbot.data.entity.UserEntity;
import ru.qwerty.schedulerbot.data.model.Response;
import ru.qwerty.schedulerbot.data.model.UserChanges;
import ru.qwerty.schedulerbot.exception.DuplicateDataException;
import ru.qwerty.schedulerbot.exception.ObjectNotFoundException;

/**
 * The default implementation of the {@link UserService} interface.
 */
@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserEntity getById(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(Response.USER_NOT_FOUND)
        );
    }

    @Override
    @Transactional
    public void save(UserEntity user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            throw new DuplicateDataException(Response.USER_WAS_ALREADY_REGISTERED);
        }

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(long id, UserChanges userChanges) {
        UserEntity user = getById(id);

        if (userChanges.getGroup() != null) {
            user.setGroup(userChanges.getGroup());
        }
        if (userChanges.getIsSubscribed() != null) {
            user.setIsSubscribed(userChanges.getIsSubscribed());
        }
    }
}
