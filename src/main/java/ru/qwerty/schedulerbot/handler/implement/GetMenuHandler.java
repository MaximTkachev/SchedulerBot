package ru.qwerty.schedulerbot.handler.implement;

import org.springframework.stereotype.Component;
import ru.qwerty.schedulerbot.data.model.Command;
import ru.qwerty.schedulerbot.data.model.Message;
import ru.qwerty.schedulerbot.handler.Handler;
import ru.qwerty.schedulerbot.message.MessageFactory;
import ru.qwerty.schedulerbot.message.MessageKey;

/**
 * The handler is used for the case when a user wants to get a list of available bot commands.
 */
@Component
public class GetMenuHandler implements Handler {

    @Override
    public String handle(Message message) {
        return MessageFactory.createMessage(
                message.getLanguage(),
                MessageKey.MENU_RESPONSE,
                Command.GET_GROUP,
                Command.GET_SCHEDULE,
                Command.SET_GROUP,
                Command.SUBSCRIBE,
                Command.UNSUBSCRIBE
        );
    }
}
