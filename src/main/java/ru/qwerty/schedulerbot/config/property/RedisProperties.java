package ru.qwerty.schedulerbot.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The property class is used to store constants for Redis.
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "redis")
public class RedisProperties {

    private String hostName;

    private Integer port;
}
