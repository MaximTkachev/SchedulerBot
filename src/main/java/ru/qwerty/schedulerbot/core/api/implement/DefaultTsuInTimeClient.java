package ru.qwerty.schedulerbot.core.api.implement;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.qwerty.schedulerbot.config.property.InTimeProperties;
import ru.qwerty.schedulerbot.core.api.TsuInTimeClient;
import ru.qwerty.schedulerbot.core.util.SerializationUtils;
import ru.qwerty.schedulerbot.data.model.dto.Schedule;
import ru.qwerty.schedulerbot.data.model.dto.Group;
import ru.qwerty.schedulerbot.exception.TimeoutException;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public class DefaultTsuInTimeClient implements TsuInTimeClient {

    private static final TypeReference<List<Group>> GROUP_LIST_TYPE_REFERENCE = new TypeReference<>() {};

    private static final TypeReference<List<Schedule>> SCHEDULE_LIST_TYPE_REFERENCE = new TypeReference<>() {};

    private final Clock clock;

    private final AtomicLong lastRequestForGroupsMillis;

    private final String getGroupsUrl;

    private final String getScheduleUrlTemplate;

    private final long requestTimeoutMillis;

    public DefaultTsuInTimeClient(Clock clock, InTimeProperties properties) {
        this.clock = clock;
        this.lastRequestForGroupsMillis = new AtomicLong();
        this.getGroupsUrl = properties.getHost() + "/api/web/v1/faculties/aa30cf34-6279-11e9-8107-005056bc52bb/groups";
        this.getScheduleUrlTemplate = properties.getHost() + "/api/web/v1/schedule/group?id=%s&dateFrom=%s&dateTo=%s";
        this.requestTimeoutMillis = properties.getRequestTimeoutMillis();
    }

    @Override
    public List<Group> getGroups() {
        if (lastRequestForGroupsMillis.get() + requestTimeoutMillis > clock.millis()) {
            log.warn("Unable to send request for groups due to timeout");
            throw new TimeoutException();
        }
        lastRequestForGroupsMillis.set(clock.millis());

        return SerializationUtils.deserialize(sendGetRequest(getGroupsUrl), GROUP_LIST_TYPE_REFERENCE);
    }

    @Override
    public Schedule getSchedule(String groupId, Date date) {
        String dateString = convertDateToString(date);
        String response = sendGetRequest(String.format(getScheduleUrlTemplate, groupId, dateString, dateString));
        return SerializationUtils.deserialize(response, SCHEDULE_LIST_TYPE_REFERENCE).get(0);
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
