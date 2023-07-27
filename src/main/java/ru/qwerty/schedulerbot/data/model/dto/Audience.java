package ru.qwerty.schedulerbot.data.model.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class Audience {

    String id;

    String name;

    String shortName;

    Building building;
}