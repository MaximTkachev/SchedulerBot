package ru.qwerty.schedulerbot.service.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.qwerty.schedulerbot.entity.UserEntity;
import ru.qwerty.schedulerbot.exception.DuplicateDataException;
import ru.qwerty.schedulerbot.exception.ObjectNotFoundException;
import ru.qwerty.schedulerbot.model.Response;
import ru.qwerty.schedulerbot.repository.UserRepository;
import ru.qwerty.schedulerbot.service.UserService;

/**
 * The default implementation of the {@link UserService} interface.
 */
@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserEntity findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Response.USER_NOT_FOUND));
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
    public void update(UserEntity newData, long id) {
        UserEntity user = findById(id);

        if (newData.getGroup() != null) {
            user.setGroup(newData.getGroup());
        }
    }
}
