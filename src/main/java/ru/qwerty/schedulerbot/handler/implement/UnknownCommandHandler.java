package ru.qwerty.schedulerbot.handler.implement;

import org.springframework.stereotype.Component;
import ru.qwerty.schedulerbot.data.model.Command;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.handler.Handler;
import ru.qwerty.schedulerbot.message.MessageFactory;
import ru.qwerty.schedulerbot.message.MessageKey;

/**
 * The handler is used for the case when a user sent an invalid command.
 */
@Component
public class UnknownCommandHandler implements Handler {

    @Override
    public String handle(Message message) {
        return MessageFactory.createMessage(
                message.getLanguage(),
                MessageKey.UNKNOWN_COMMAND_RESPONSE,
                Command.SET_GROUP
        );
    }
}
