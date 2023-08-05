package ru.qwerty.schedulerbot.core.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.qwerty.schedulerbot.data.model.dto.DaySchedule;
import ru.qwerty.schedulerbot.data.model.dto.Lesson;
import ru.qwerty.schedulerbot.exception.UnexpectedServerDataException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public final class Mapper {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final String EMPTY_SCHEDULE_TEMPLATE = "%s пар нет. Поздравляем!";

    private static final String TITLE_TEMPLATE = "Ваше расписание на %s:\n";

    private static final String LESSON_MAPPING_TEMPLATE = "%s - %s (%s)";

    private Mapper() {
    }

    public static <T> T deserialize(String value, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(value, typeReference);
        } catch (Exception e) {
            throw new UnexpectedServerDataException();
        }
    }

    public static String serialize(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return object.toString();
        }
    }

    public static String serializeDaySchedule(DaySchedule daySchedule) {
        daySchedule = filterSchedule(daySchedule);
        if (daySchedule.getLessons().isEmpty()) {
            return String.format(EMPTY_SCHEDULE_TEMPLATE, convertStringDate(daySchedule.getDate()));
        }

        StringBuilder sb = new StringBuilder(String.format(TITLE_TEMPLATE, convertStringDate(daySchedule.getDate())));
        daySchedule.getLessons().forEach(lesson -> sb.append(mapLesson(lesson)));
        return sb.toString();
    }

    private static DaySchedule filterSchedule(DaySchedule schedule) {
        List<Lesson> lessons = schedule.getLessons()
                .stream()
                .filter(lesson -> lesson.getType() != Lesson.ScheduleType.EMPTY)
                .collect(Collectors.toList());

        return DaySchedule.builder()
                .date(schedule.getDate())
                .lessons(lessons)
                .build();
    }

    private static String mapLesson(Lesson lesson) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lesson.getGroups().size() - 1; i++) {
            sb.append(lesson.getGroups().get(i).getName()).append(", ");
        }
        sb.append(lesson.getGroups().get(lesson.getGroups().size() - 1).getName());

        String startTime = convertTime(new Date(lesson.getStarts() * 1000L));
        return String.format(LESSON_MAPPING_TEMPLATE, startTime, lesson.getTitle(), sb) + "\n";
    }

    private static String convertStringDate(String date) {
        SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormatter = new SimpleDateFormat("dd.MM.yyyy");

        try {
            return outputFormatter.format(inputFormatter.parse(date));
        } catch (ParseException e) {
            throw new UnexpectedServerDataException();
        }
    }

    private static String convertTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Asia/Novosibirsk")));
        return simpleDateFormat.format(date);
    }
}
