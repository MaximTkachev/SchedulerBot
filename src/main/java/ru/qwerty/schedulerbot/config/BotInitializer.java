package ru.qwerty.schedulerbot.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.qwerty.schedulerbot.TelegramBot;

/**
 * The component is used to initialize the bot in the telegram api.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BotInitializer {

    private final TelegramBot bot;

    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(bot);
            log.info("Bot was successfully initialized");
        } catch (Exception e) {
            log.error("Bot initialization failed", e);
            throw new RuntimeException(e);
        }
    }
}
