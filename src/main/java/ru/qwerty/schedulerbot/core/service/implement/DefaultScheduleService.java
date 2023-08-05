package ru.qwerty.schedulerbot.core.service.implement;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import ru.qwerty.schedulerbot.core.api.RequestManager;
import ru.qwerty.schedulerbot.core.service.ScheduleService;
import ru.qwerty.schedulerbot.core.util.Mapper;
import ru.qwerty.schedulerbot.data.redis.ScheduleKey;
import ru.qwerty.schedulerbot.data.redis.RedisCache;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The default implementation of the {@link ScheduleService} interface.
 */
@Service
public class DefaultScheduleService implements ScheduleService {

    private final Cache cache;

    private final RequestManager requestManager;

    public DefaultScheduleService(CacheManager cacheManager, RequestManager requestManager) {
        this.cache = cacheManager.getCache(RedisCache.SCHEDULE);
        this.requestManager = requestManager;
    }

    @Override
    public String get(String groupId, Date date) {
        ScheduleKey key = new ScheduleKey(groupId, serializeDate(date));
        String schedule = cache.get(key, String.class);
        if (schedule != null) {
            return schedule;
        }
        schedule = Mapper.serializeDaySchedule(requestManager.fetchSchedule(groupId, date));
        cache.put(key, schedule);
        return schedule;
    }

    private static String serializeDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(date);
    }
}
