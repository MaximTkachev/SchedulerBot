package ru.qwerty.schedulerbot.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.qwerty.schedulerbot.exception.StrangeServerDataException;
import ru.qwerty.schedulerbot.model.dto.DaySchedule;
import ru.qwerty.schedulerbot.model.dto.Group;
import ru.qwerty.schedulerbot.model.dto.Lesson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public final class Mapper {

    private static final ObjectMapper OBJECT_MAPPER;

    private static final String TITLE_TEMPLATE = "Ваше расписание на %s:\n";

    private static final String LESSON_MAPPING_TEMPLATE = "%s - %s (%s)";

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private Mapper() {
    }

    public static List<Group> deserializeGroups(String value) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(value, new TypeReference<>() {});
    }

    public static List<DaySchedule> deserializeDaySchedules(String value) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(value, new TypeReference<>() {});
    }

    public static String serializeDaySchedule(DaySchedule daySchedule) {
        daySchedule.filter();
        StringBuilder sb = new StringBuilder(String.format(TITLE_TEMPLATE, convertStringDate(daySchedule.getDate())));
        daySchedule.getLessons().forEach(lesson -> sb.append(mapLesson(lesson)));
        return sb.toString();
    }

    private static String mapLesson(Lesson lesson) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lesson.getGroups().size() - 1; i++) {
            sb.append(lesson.getGroups().get(i).getName()).append(", ");
        }
        sb.append(lesson.getGroups().get(lesson.getGroups().size() - 1).getName());

        String startTime = convertTime(new Date(lesson.getStarts() * 1000));
        return String.format(LESSON_MAPPING_TEMPLATE, startTime, lesson.getTitle(), sb) + "\n";
    }

    private static String convertStringDate(String date) {
        SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormatter = new SimpleDateFormat("dd.MM.yyyy");
        try {
            return outputFormatter.format(inputFormatter.parse(date));
        } catch (ParseException e) {
            throw new StrangeServerDataException();
        }
    }

    private static String convertTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Asia/Novosibirsk")));
        return simpleDateFormat.format(date);
    }
}
