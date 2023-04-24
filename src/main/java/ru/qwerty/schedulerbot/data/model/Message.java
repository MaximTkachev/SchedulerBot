package ru.qwerty.schedulerbot.data.model;

import lombok.ToString;
import lombok.Value;

@Value
@ToString
public class Message {

    Long id;

    String text;
}
