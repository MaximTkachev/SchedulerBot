package ru.qwerty.schedulerbot.core.handler.implement;

import org.springframework.stereotype.Component;
import ru.qwerty.schedulerbot.core.handler.Handler;
import ru.qwerty.schedulerbot.data.model.Command;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.i18n.MessageFactory;
import ru.qwerty.schedulerbot.i18n.MessageKey;

/**
 * The handler is used for the case when a user sent an invalid command.
 */
@Component
public class UnknownCommandHandler implements Handler {

    @Override
    public Command getCommand() {
        return null;
    }

    @Override
    public String handle(Message message) {
        return MessageFactory.createMessage(
                message.getLanguage(),
                MessageKey.UNKNOWN_COMMAND_RESPONSE,
                Command.GET_MENU
        );
    }
}
