package ru.qwerty.schedulerbot.model.dto;

import lombok.Data;

@Data
public class Building {

    private String id;

    private String name;

    private String address;

    private Double latitude;

    private Double longitude;
}
