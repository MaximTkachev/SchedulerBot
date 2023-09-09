package ru.qwerty.schedulerbot.data.model;

import lombok.ToString;
import lombok.Value;
import ru.qwerty.schedulerbot.message.Language;

@Value
@ToString
public class Message {

    Long chatId;

    String text;

    Language language;
}
