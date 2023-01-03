package ru.qwerty.schedulerbot.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * The class in used to map instances of {@link Update} class to strings.
 */
@Slf4j
public class UpdateMapper {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static String mapForLog(Update update) {
        try {
            return OBJECT_MAPPER.writeValueAsString(update);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize object: {}", update, e);
            return update.toString();
        }
    }
}
