package ru.qwerty.schedulerbot.converter;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.qwerty.schedulerbot.entity.UserEntity;

/**
 * The interface is used to convert instances of classes associated with users.
 */
public interface UserConverter {

    UserEntity convertToEntity(Update update);
}
