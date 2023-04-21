package ru.qwerty.schedulerbot.data.converter;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.qwerty.schedulerbot.data.entity.UserEntity;

/**
 * The interface is used to convert instances of classes associated with users.
 */
public interface UserConverter {

    UserEntity convertToEntity(Update update);
}
