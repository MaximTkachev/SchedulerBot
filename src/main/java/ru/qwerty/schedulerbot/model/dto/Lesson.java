package ru.qwerty.schedulerbot.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class Lesson {

    public enum ScheduleType {
        EMPTY,
        LESSON
    }

    private ScheduleType type;

    private String id;

    private String title;

    private LessonType lessonType;

    private List<Group> groups;

    private Professor professor;

    private Audience audience;

    private Integer lessonNumber;

    private Integer starts;

    private Integer ends;

    public Boolean isNotEmpty() {
        return type != ScheduleType.EMPTY;
    }
}
