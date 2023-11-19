package ru.qwerty.schedulerbot.i18n;

import ru.qwerty.schedulerbot.data.model.dto.Lesson;
import ru.qwerty.schedulerbot.data.model.dto.Schedule;
import ru.qwerty.schedulerbot.exception.UnexpectedServerDataException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.stream.Collectors;

public final class MessageFactory {

    private static final Map<Language, ResourceBundle> RESOURCE_BUNDLES = new EnumMap<>(Language.class);

    static {
        for (Language language : Language.values()) {
            RESOURCE_BUNDLES.put(language, ResourceBundle.getBundle("bundle.messages", language.getLocale()));
        }
    }

    private MessageFactory() {
    }

    public static String createMessage(Language language, MessageKey key) {
        return RESOURCE_BUNDLES.get(language).getString(key.getValue());
    }

    public static String createMessage(Language language, MessageKey key, Object... args) {
        return String.format(createMessage(language, key), args);
    }

    public static String createSchedule(Language language, Schedule schedule) {
        schedule = filterSchedule(schedule);
        ResourceBundle resourceBundle = RESOURCE_BUNDLES.get(language);

        if (schedule.getLessons().isEmpty()) {
            String emptyScheduleTemplate = resourceBundle.getString(MessageKey.EMPTY_SCHEDULE_TEMPLATE.getValue());
            return String.format(emptyScheduleTemplate, convertStringDate(schedule.getDate()));
        }

        String titleTemplate = resourceBundle.getString(MessageKey.TITLE_TEMPLATE.getValue());
        StringBuilder sb = new StringBuilder(String.format(titleTemplate, convertStringDate(schedule.getDate())));
        schedule.getLessons().forEach(lesson -> sb.append(createLesson(lesson)));
        return sb.toString();
    }

    private static Schedule filterSchedule(Schedule schedule) {
        List<Lesson> lessons = schedule.getLessons()
                .stream()
                .filter(lesson -> lesson.getType() != Lesson.ScheduleType.EMPTY)
                .collect(Collectors.toList());

        return Schedule.builder()
                .date(schedule.getDate())
                .lessons(lessons)
                .build();
    }

    private static String createLesson(Lesson lesson) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lesson.getGroups().size() - 1; i++) {
            sb.append(lesson.getGroups().get(i).getName()).append(", ");
        }
        sb.append(lesson.getGroups().get(lesson.getGroups().size() - 1).getName());

        String startTime = convertTime(new Date(lesson.getStarts() * 1000L));
        return String.format("%s - %s (%s)", startTime, lesson.getTitle(), sb) + "\n";
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
