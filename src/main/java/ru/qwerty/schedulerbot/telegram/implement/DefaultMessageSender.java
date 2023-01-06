package ru.qwerty.schedulerbot.telegram.implement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.qwerty.schedulerbot.config.BotConfig;
import ru.qwerty.schedulerbot.telegram.MessageSender;

/**
 * The default implementation of the {@link MessageSender} interface.
 */
@Slf4j
@Component
public class DefaultMessageSender extends DefaultAbsSender implements MessageSender {

    private final BotConfig config;

    public DefaultMessageSender(BotConfig config) {
        super(new DefaultBotOptions());
        this.config = config;
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void send(long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);

        try {
            execute(sendMessage);
            log.info("Message: {} was successfully sent to chat: {}", prepareMessageForLog(message), chatId);
        } catch (Exception e) {
            log.error("Failed to send message ", e);
        }
    }

    private static String prepareMessageForLog(String message) {
        return message.replaceAll("\n", "");
    }
}
