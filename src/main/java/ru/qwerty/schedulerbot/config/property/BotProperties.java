package ru.qwerty.schedulerbot.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The property class is used to store constants for the bot to work.
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "bot")
public class BotProperties {

    private String username;

    private String token;

    private Long requestTimeoutMillis;
}
