package ru.qwerty.schedulerbot.data.model.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class Building {

    String id;

    String name;

    String address;

    Double latitude;

    Double longitude;
}
