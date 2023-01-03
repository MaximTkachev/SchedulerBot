package ru.qwerty.schedulerbot.service.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.qwerty.schedulerbot.entity.UserEntity;
import ru.qwerty.schedulerbot.exception.DuplicateDataException;
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

    @Override
    public void save(UserEntity user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            throw new DuplicateDataException(Response.USER_WAS_ALREADY_REGISTERED);
        }

        userRepository.save(user);
    }
}
