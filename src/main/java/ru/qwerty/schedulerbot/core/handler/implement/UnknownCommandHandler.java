package ru.qwerty.schedulerbot.core.handler.implement;

import org.springframework.stereotype.Component;
import ru.qwerty.schedulerbot.core.handler.AbstractHandler;
import ru.qwerty.schedulerbot.core.service.UserService;
import ru.qwerty.schedulerbot.data.model.Command;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.i18n.MessageFactory;
import ru.qwerty.schedulerbot.i18n.MessageKey;

/**
 * The handler is used for the case when a user sent an invalid command.
 */
@Component
public class UnknownCommandHandler extends AbstractHandler {

    public UnknownCommandHandler(UserService userService) {
        super(userService);
    }

    @Override
    public Command getCommand() {
        return Command.UNKNOWN;
    }

    @Override
    public String doHandle(Message message) {
        return MessageFactory.createMessage(
                message.getLanguage(),
                MessageKey.UNKNOWN_COMMAND_RESPONSE,
                Command.GET_MENU
        );
    }
}
