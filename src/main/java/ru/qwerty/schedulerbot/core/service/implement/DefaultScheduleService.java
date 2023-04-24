package ru.qwerty.schedulerbot.core.service.implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import ru.qwerty.schedulerbot.core.api.RequestManager;
import ru.qwerty.schedulerbot.core.service.ScheduleService;
import ru.qwerty.schedulerbot.core.util.Mapper;
import ru.qwerty.schedulerbot.data.redis.ScheduleKey;
import ru.qwerty.schedulerbot.data.redis.RedisCache;
import ru.qwerty.schedulerbot.data.model.dto.DaySchedule;
import ru.qwerty.schedulerbot.exception.UnexpectedServerDataException;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        DaySchedule scheduleDto = fetchSchedule(groupId, date);
        schedule = Mapper.serializeDaySchedule(scheduleDto);
        cache.put(key, schedule);
        return schedule;
    }

    private static String serializeDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(date);
    }

    private DaySchedule fetchSchedule(String groupId, Date date) {
        try {
            return requestManager.fetchSchedule(groupId, date);
        } catch (JsonProcessingException e) {
            throw new UnexpectedServerDataException();
        }
    }
}
