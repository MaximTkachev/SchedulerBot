package ru.qwerty.schedulerbot.data.model.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class Lesson {

    public enum ScheduleType {
        EMPTY,
        LESSON
    }

    ScheduleType type;

    String id;

    String title;

    LessonType lessonType;

    List<Group> groups;

    Professor professor;

    Audience audience;

    Integer lessonNumber;

    Integer starts;

    Integer ends;

}
