package ru.qwerty.schedulerbot.data.converter.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.qwerty.schedulerbot.data.converter.UserMapper;
import ru.qwerty.schedulerbot.data.entity.UserEntity;
import ru.qwerty.schedulerbot.data.model.Message;

import java.time.Clock;
import java.util.Date;

/**
 * The default implementation of the {@link UserMapper} interface.
 */
@Component
@RequiredArgsConstructor
public class DefaultUserMapper implements UserMapper {

    private final Clock clock;

    @Override
    public UserEntity map(Message message) {
        return UserEntity.builder()
                .id(message.getChatId())
                .creationDate(new Date(clock.millis()))
                .isSubscribed(false)
                .build();
    }
}
