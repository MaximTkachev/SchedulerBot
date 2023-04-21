package ru.qwerty.schedulerbot.handler.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.qwerty.schedulerbot.core.service.UserService;
import ru.qwerty.schedulerbot.core.util.Mapper;
import ru.qwerty.schedulerbot.data.converter.UserConverter;
import ru.qwerty.schedulerbot.data.entity.UserEntity;
import ru.qwerty.schedulerbot.data.model.Command;
import ru.qwerty.schedulerbot.handler.Handler;

/**
 * The handler is used to register user when they send the first message.
 */
@Slf4j
@RequiredArgsConstructor
public class StartHandler implements Handler {

    private final UserConverter converter;

    private final UserService service;

    @Override
    public String handle(Update update) {
        UserEntity user = converter.convertToEntity(update);
        service.save(user);
        log.info("User was registered from update; {}", Mapper.mapForLog(update));
        return "Добро пожаловать!\nВведите " + Command.GET_MENU + " чтобы получить список доступных команд";
    }
}
