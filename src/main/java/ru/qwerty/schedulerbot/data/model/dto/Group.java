package ru.qwerty.schedulerbot.data.model.dto;

import lombok.Data;

@Data
public class Group {

    private String id;

    private String name;

    private Boolean isSubgroup;
}