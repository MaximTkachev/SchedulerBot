package ru.qwerty.schedulerbot.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.qwerty.schedulerbot.model.dto.Group;

import java.util.List;

public class Mapper {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static List<Group> mapGroups(String value) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(value, new TypeReference<>() {});
    }
}
