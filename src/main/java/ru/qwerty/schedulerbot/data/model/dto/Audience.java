package ru.qwerty.schedulerbot.data.model.dto;

import lombok.Data;

@Data
public class Audience {

    String id;

    String name;

    String shortName;

    Building building;
}