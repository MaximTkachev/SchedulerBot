package ru.qwerty.schedulerbot.core.metric;

import io.micrometer.core.instrument.Meter;

public interface PrometheusMetricService {

    <T extends Meter> T get(String name);
}
