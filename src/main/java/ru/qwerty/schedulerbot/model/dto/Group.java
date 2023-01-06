package ru.qwerty.schedulerbot.model.dto;

import lombok.Data;

@Data
public class Group {

    private String id;

    private String name;

    private Boolean isSubgroup;
}