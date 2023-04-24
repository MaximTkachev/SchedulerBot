package ru.qwerty.schedulerbot.core.api.implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.qwerty.schedulerbot.config.BotProperties;
import ru.qwerty.schedulerbot.core.api.RequestManager;
import ru.qwerty.schedulerbot.core.util.Mapper;
import ru.qwerty.schedulerbot.data.model.Response;
import ru.qwerty.schedulerbot.data.model.dto.DaySchedule;
import ru.qwerty.schedulerbot.data.model.dto.Group;
import ru.qwerty.schedulerbot.exception.ActionNotAllowedException;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class WebClientRequestManager implements RequestManager {

    private static final String GET_GROUPS_URL
            = "https://intime.tsu.ru/api/web/v1/faculties/aa30cf34-6279-11e9-8107-005056bc52bb/groups";

    private static final String GET_SCHEDULE_URL_TEMPLATE
            = "https://intime.tsu.ru/api/web/v1/schedule/group?id=%s&dateFrom=%s&dateTo=%s";

    private static final TypeReference<List<Group>> GROUP_LIST_TYPE_REFERENCE = new TypeReference<>() {};

    private static final TypeReference<List<DaySchedule>> DAY_SCHEDULE_LIST_TYPE_REFERENCE = new TypeReference<>() {};

    private final Clock clock;

    private final long requestTimeoutMillis;

    private long lastRequestForGroupsMillis;

    public WebClientRequestManager(Clock clock, BotProperties config) {
        this.clock = clock;
        this.requestTimeoutMillis = config.getRequestTimeoutMillis();
        this.lastRequestForGroupsMillis = 1;
    }

    @Override
    public List<Group> fetchGroups() throws JsonProcessingException {
        if (lastRequestForGroupsMillis + requestTimeoutMillis > clock.millis()) {
            log.warn("It's too early to send request for groups");
            throw new ActionNotAllowedException(Response.ACTION_NOT_ALLOWED);
        }
        lastRequestForGroupsMillis = clock.millis();

        return Mapper.deserialize(sendGetRequest(GET_GROUPS_URL), GROUP_LIST_TYPE_REFERENCE);
    }

    @Override
    public DaySchedule fetchSchedule(String groupId, Date date) throws JsonProcessingException {
        String dateString = convertDateToString(date);
        String response = sendGetRequest(String.format(GET_SCHEDULE_URL_TEMPLATE, groupId, dateString, dateString));
        return Mapper.deserialize(response, DAY_SCHEDULE_LIST_TYPE_REFERENCE).get(0);
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
