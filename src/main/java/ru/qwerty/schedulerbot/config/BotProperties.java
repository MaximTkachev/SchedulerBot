package ru.qwerty.schedulerbot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The component is used to get and store constants for the bot to work.
 */
@Getter
@Component
public class BotProperties {

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    @Value("${bot.request-timeout-millis}")
    private long requestTimeoutMillis;
}
