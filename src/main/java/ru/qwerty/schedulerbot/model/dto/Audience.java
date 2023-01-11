package ru.qwerty.schedulerbot.model.dto;

import lombok.Data;

@Data
public class Audience {

    String id;

    String name;

    String shortName;

    Building building;
}