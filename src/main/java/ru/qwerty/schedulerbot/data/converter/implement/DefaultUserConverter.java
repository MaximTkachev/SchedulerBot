package ru.qwerty.schedulerbot.data.converter.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.qwerty.schedulerbot.data.converter.UserConverter;
import ru.qwerty.schedulerbot.data.entity.UserEntity;
import ru.qwerty.schedulerbot.data.model.Message;

import java.time.Clock;
import java.util.Date;

/**
 * The default implementation of the {@link UserConverter} interface.
 */
@Component
@RequiredArgsConstructor
public class DefaultUserConverter implements UserConverter {

    private final Clock clock;

    @Override
    public UserEntity convertToEntity(Message message) {
        UserEntity user = new UserEntity();
        user.setId(message.getId());
        user.setCreationDate(new Date(clock.millis()));
        user.setIsSubscribed(false);
        return user;
    }
}
