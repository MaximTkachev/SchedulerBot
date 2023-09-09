package ru.qwerty.schedulerbot.core.service.implement;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import ru.qwerty.schedulerbot.client.TsuInTimeClient;
import ru.qwerty.schedulerbot.core.service.ScheduleService;
import ru.qwerty.schedulerbot.core.util.SerializationUtils;
import ru.qwerty.schedulerbot.data.model.dto.Schedule;
import ru.qwerty.schedulerbot.data.redis.ScheduleKey;
import ru.qwerty.schedulerbot.data.redis.RedisCache;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The default implementation of the {@link ScheduleService} interface.
 */
@Slf4j
@Service
public class DefaultScheduleService implements ScheduleService {

    private static final TypeReference<Schedule> SCHEDULE_TYPE_REFERENCE = new TypeReference<>() {};

    private final Cache cache;

    private final TsuInTimeClient tsuInTimeClient;

    public DefaultScheduleService(CacheManager cacheManager, TsuInTimeClient tsuInTimeClient) {
        this.cache = cacheManager.getCache(RedisCache.SCHEDULE);
        this.tsuInTimeClient = tsuInTimeClient;
    }

    @Override
    public Schedule get(String groupId, Date date) {
        log.info("Get schedule: groupId = {}, date = {}", groupId, date);
        ScheduleKey key = new ScheduleKey(groupId, serializeDate(date));
        String cachedSchedule = cache.get(key, String.class);
        if (cachedSchedule != null) {
            return SerializationUtils.deserialize(cachedSchedule, SCHEDULE_TYPE_REFERENCE);
        }
        Schedule schedule = tsuInTimeClient.getSchedule(groupId, date);
        cache.put(key, SerializationUtils.serialize(schedule));
        return schedule;
    }

    private static String serializeDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(date);
    }
}
