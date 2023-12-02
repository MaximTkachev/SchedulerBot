package ru.qwerty.schedulerbot.config.scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.qwerty.schedulerbot.core.service.MailingService;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledManager {

    private final MailingService mailingService;

    @Scheduled(cron = "0 0 8 * * ?", zone = "Asia/Novosibirsk")
    public void sendScheduling() {
        log.info("The daily schedule newsletter started");

        try {
            mailingService.send();
        } finally {
            log.info("The daily schedule newsletter finished");
        }
    }
}
