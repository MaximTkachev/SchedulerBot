package ru.qwerty.schedulerbot.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.qwerty.schedulerbot.config.property.BotProperties;

@Slf4j
@Component
class MessageSender extends DefaultAbsSender {

    private final String botToken;

    MessageSender(BotProperties properties) {
        super(new DefaultBotOptions());
        this.botToken = properties.getToken();
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void send(long chatId, String message) {
        try {
            execute(new SendMessage(Long.toString(chatId), message));
            log.info("Message: {} was successfully sent to chat: {}", message, chatId);
        } catch (Exception e) {
            log.error("Failed to send message ", e);
        }
    }
}
