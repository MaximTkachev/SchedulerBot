package ru.qwerty.schedulerbot.model.dto;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class DaySchedule {

    private String date;

    private List<Lesson> lessons;

    public void filter() {
        this.lessons = lessons.stream().filter(Lesson::isNotEmpty).collect(Collectors.toList());
    }
}
