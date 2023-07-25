package ru.qwerty.schedulerbot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.qwerty.schedulerbot.config.property.BotProperties;
import ru.qwerty.schedulerbot.config.property.RedisProperties;

import java.time.Clock;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * The configuration file contains Spring Beans.
 */
@Configuration
@EnableConfigurationProperties({BotProperties.class, RedisProperties.class})
public class AppConfig {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public ThreadPoolExecutor threadPoolExecutor(@Value("${system.max-thread-number}") int maxThreadNumber) {
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(maxThreadNumber);
    }
}
