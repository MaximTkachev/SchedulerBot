package ru.qwerty.schedulerbot.telegram;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.qwerty.schedulerbot.exception.InternalException;

/**
 * The component is used to initialize the bot in the telegram api.
 */
@Slf4j
@Component
@RequiredArgsConstructor
class BotInitializer {

    private final TelegramBot bot;

    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
            log.info("Bot was successfully initialized");
        } catch (Exception e) {
            log.error("Bot initialization failed", e);
            throw new InternalException();
        }
    }
}
