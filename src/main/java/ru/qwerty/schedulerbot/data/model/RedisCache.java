package ru.qwerty.schedulerbot.data.model;

import java.util.Set;

public final class RedisCache {

    public static final String SCHEDULE = "Schedule";

    public static final Set<String> ALL = Set.of(SCHEDULE);

    private RedisCache() {
    }
}
