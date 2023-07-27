package ru.qwerty.schedulerbot.data.converter;

import ru.qwerty.schedulerbot.data.entity.UserEntity;
import ru.qwerty.schedulerbot.data.model.Message;

/**
 * The interface is used to convert instances of classes associated with users.
 */
public interface UserConverter {

    UserEntity map(Message message);
}
