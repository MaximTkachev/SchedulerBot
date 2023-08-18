package ru.qwerty.schedulerbot.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The property class is used to store constants for work with TSU InTime API.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "in-time")
public class InTimeProperties {

    private String host;

    private Long requestTimeoutMillis;
}
