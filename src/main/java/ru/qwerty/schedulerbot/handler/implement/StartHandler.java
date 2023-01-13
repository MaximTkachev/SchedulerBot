package ru.qwerty.schedulerbot.handler.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.qwerty.schedulerbot.converter.UserConverter;
import ru.qwerty.schedulerbot.entity.UserEntity;
import ru.qwerty.schedulerbot.mapper.UpdateMapper;
import ru.qwerty.schedulerbot.model.Command;
import ru.qwerty.schedulerbot.handler.Handler;
import ru.qwerty.schedulerbot.service.UserService;

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
        log.info("User was registered from update; {}", UpdateMapper.mapForLog(update));
        return "Добро пожаловать!\nВведите " + Command.GET_MENU + " чтобы получить список доступных команд";
    }
}
