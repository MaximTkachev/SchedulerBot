package ru.qwerty.schedulerbot.api.implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.qwerty.schedulerbot.api.RequestManager;
import ru.qwerty.schedulerbot.config.BotConfig;
import ru.qwerty.schedulerbot.exception.ActionNotAllowedException;
import ru.qwerty.schedulerbot.mapper.Mapper;
import ru.qwerty.schedulerbot.model.dto.DaySchedule;
import ru.qwerty.schedulerbot.model.dto.Group;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class WebClientRequestManager implements RequestManager {

    private final static String GET_GROUPS_URL
            = "https://intime.tsu.ru/api/web/v1/faculties/aa30cf34-6279-11e9-8107-005056bc52bb/groups";

    private final static String GET_SCHEDULE_URL_TEMPLATE
            = "https://intime.tsu.ru/api/web/v1/schedule/group?id=%s&dateFrom=%s&dateTo=%s";

    private final Clock clock;

    private final long requestTimeoutMillis;

    private long lastRequestForGroupsMillis;

    public WebClientRequestManager(Clock clock, BotConfig config) {
        this.clock = clock;
        this.requestTimeoutMillis = config.getRequestTimeoutMillis();
        this.lastRequestForGroupsMillis = 1;
    }

    @Override
    public List<Group> fetchGroups() throws JsonProcessingException {
        if (lastRequestForGroupsMillis + requestTimeoutMillis > clock.millis()) {
            log.warn("It's too early to send request for groups");
            throw new ActionNotAllowedException("It's too early to send request");
        }
        lastRequestForGroupsMillis = clock.millis();
        
        String response = sendGetRequest(GET_GROUPS_URL);
        try {
            List<Group> groups = Mapper.deserializeGroups(response);
            log.info("Groups were successfully received from server: {}", response);
            return groups;
        } catch (Exception e) {
            log.error("Failed to map response: {}", response, e);
            throw e;
        }
    }

    @Override
    public DaySchedule fetchSchedule(String groupId, Date date) throws JsonProcessingException {
        String dateString = convertDateToString(date);
        String response = sendGetRequest(String.format(GET_SCHEDULE_URL_TEMPLATE, groupId, dateString, dateString));

        try {
            DaySchedule schedule = Mapper.deserializeDaySchedules(response).get(0);
            log.info("Schedule was successfully received from server: {}", response);
            return schedule;
        } catch (JsonProcessingException e) {
            log.error("Failed to map response: {}", response, e);
            throw e;
        }
    }

    private String convertDateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    private static String sendGetRequest(String uri) {
        return WebClient.create()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .log()
                .block();
    }
}
