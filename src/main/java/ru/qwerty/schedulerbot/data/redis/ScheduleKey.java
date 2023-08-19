package ru.qwerty.schedulerbot.data.redis;

import lombok.Value;

@Value
public class ScheduleKey {

    String groupNumber;

    String date;

    @Override
    public String toString() {
        return groupNumber + "-" + date;
    }
}
