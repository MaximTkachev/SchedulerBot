package ru.qwerty.schedulerbot.data.entity.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleKey {

    private String groupNumber;

    private String date;

    @Override
    public String toString() {
        return groupNumber + "-" + date;
    }
}
