package ru.qwerty.schedulerbot.core.client.implement;

import io.micrometer.core.instrument.Counter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;
import ru.qwerty.schedulerbot.config.property.InTimeProperties;
import ru.qwerty.schedulerbot.core.client.TsuInTimeClient;
import ru.qwerty.schedulerbot.core.metric.PrometheusMetricService;
import ru.qwerty.schedulerbot.core.util.WaitUtils;
import ru.qwerty.schedulerbot.data.model.dto.Group;
import ru.qwerty.schedulerbot.data.model.dto.Schedule;
import ru.qwerty.schedulerbot.data.prometheus.PrometheusMetricNames;
import ru.qwerty.schedulerbot.exception.TimeoutException;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public class DefaultTsuInTimeClient implements TsuInTimeClient {

    private static final ParameterizedTypeReference<List<Group>> GROUP_LIST_TYPE_REFERENCE
            = new ParameterizedTypeReference<>() {};

    private static final ParameterizedTypeReference<List<Schedule>> SCHEDULE_LIST_TYPE_REFERENCE
            = new ParameterizedTypeReference<>() {};

    private final Clock clock;

    private final Counter getMenuRequestCounter;

    private final Counter getScheduleRequestCounter;

    private final AtomicLong lastRequestForGroupsMillis;

    private final String getGroupsUrl;

    private final String getScheduleUrlTemplate;

    private final long requestTimeoutMillis;

    public DefaultTsuInTimeClient(Clock clock, InTimeProperties properties, PrometheusMetricService metricService) {
        this.clock = clock;
        this.getMenuRequestCounter = metricService.get(PrometheusMetricNames.GET_GROUPS_REQUEST_COUNTER);
        this.getScheduleRequestCounter = metricService.get(PrometheusMetricNames.GET_SCHEDULE_REQUEST_COUNTER);
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
        getMenuRequestCounter.increment();
        lastRequestForGroupsMillis.set(clock.millis());

        return sendGetRequest(getGroupsUrl, GROUP_LIST_TYPE_REFERENCE);
    }

    @Override
    public Schedule getSchedule(String groupId, Date date) {
        getScheduleRequestCounter.increment();
        String dateString = convertDateToString(date);
        List<Schedule> scheduleList = sendGetRequest(
                String.format(getScheduleUrlTemplate, groupId, dateString, dateString),
                SCHEDULE_LIST_TYPE_REFERENCE
        );
        return CollectionUtils.isEmpty(scheduleList) ? null : scheduleList.get(0);
    }

    private String convertDateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    private static <T> T sendGetRequest(String uri, ParameterizedTypeReference<T> typeReference) {
        CompletableFuture<T> response = WebClient.create()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(typeReference)
                .timeout(Duration.ofSeconds(10))
                .toFuture();

        return WaitUtils.get(response);
    }
}
