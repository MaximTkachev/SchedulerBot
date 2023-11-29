package ru.qwerty.schedulerbot.core.metric.implement;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;
import ru.qwerty.schedulerbot.core.metric.PrometheusMetricService;
import ru.qwerty.schedulerbot.data.model.Command;
import ru.qwerty.schedulerbot.data.prometheus.PrometheusMetricNames;

import java.util.HashMap;
import java.util.Map;

@Service
public class DefaultPrometheusMetricService implements PrometheusMetricService {

    private final Map<String, Meter> meterMap = new HashMap<>();

    private final MeterRegistry meterRegistry;

    public DefaultPrometheusMetricService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        initiateMeters();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Meter> T get(String name) {
        return (T) meterMap.get(name);
    }

    private void initiateMeters() {
        registerCounter(PrometheusMetricNames.USER_COUNTER, "Number of registered users");
        registerCounter(PrometheusMetricNames.GET_MENU_COMMAND_CALLS_COUNTER, "Number of " + Command.GET_MENU + "command calls");
        registerCounter(PrometheusMetricNames.GET_GROUPS_REQUEST_COUNTER, "Number of requests for groups");
        registerCounter(PrometheusMetricNames.GET_SCHEDULE_REQUEST_COUNTER, "Number of requests to get a schedule");
    }

    private void registerCounter(String name, String description) {
        registerMeter(
                Counter.builder(name)
                        .description(description)
                        .register(meterRegistry)
        );
    }

    private void registerMeter(Meter meter) {
        meterMap.put(meter.getId().getName(), meter);
    }
}
