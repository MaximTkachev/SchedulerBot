package ru.qwerty.schedulerbot.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.qwerty.schedulerbot.model.dto.Group;

import java.util.List;

/**
 * The interface is used to send http requests to external services.
 */
public interface RequestManager {

    List<Group> fetchGroups() throws JsonProcessingException;
}
