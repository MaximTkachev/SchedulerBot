package ru.qwerty.schedulerbot.core.service.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.qwerty.schedulerbot.core.api.RequestManager;
import ru.qwerty.schedulerbot.core.repository.GroupRepository;
import ru.qwerty.schedulerbot.core.service.GroupService;
import ru.qwerty.schedulerbot.core.util.Validator;
import ru.qwerty.schedulerbot.data.converter.GroupConverter;
import ru.qwerty.schedulerbot.data.entity.GroupEntity;
import ru.qwerty.schedulerbot.data.model.Response;
import ru.qwerty.schedulerbot.data.model.dto.Group;
import ru.qwerty.schedulerbot.exception.ActionNotAllowedException;
import ru.qwerty.schedulerbot.exception.InternalException;
import ru.qwerty.schedulerbot.exception.ObjectNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The default implementation of the {@link GroupService} interface.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultGroupService implements GroupService {

    private final GroupConverter converter;

    private final GroupRepository repository;

    private final RequestManager requestManager;

    @Override
    @Transactional
    public GroupEntity getByNumber(String number) {
        Validator.checkGroupNumber(number);
        Optional<GroupEntity> groupEntity = repository.findByNumber(number);
        if (groupEntity.isPresent()) {
            return groupEntity.get();
        }

        List<Group> groups = fetchGroupsFromServer();
        updateGroups(groups);

        Optional<Group> group = findByNumberInList(groups, number);
        if (group.isPresent()) {
            return converter.map(group.get());
        }
        throw new ObjectNotFoundException(Response.GROUP_NOT_FOUND);
    }

    private List<Group> fetchGroupsFromServer() {
        try {
            return requestManager.fetchGroups();
        } catch (ActionNotAllowedException e) {
            throw new ObjectNotFoundException(Response.GROUP_NOT_FOUND);
        } catch (Exception e) {
            throw new InternalException();
        }
    }

    private void updateGroups(List<Group> groups) {
        List<Group> groupsForSave = new ArrayList<>();
        for (Group group : groups) {
            if (repository.findByNumber(group.getName()).isEmpty()) {
                groupsForSave.add(group);
            }
        }

        log.info("Update groups: {}", groupsForSave);
        try {
            List<GroupEntity> entities = groupsForSave.stream()
                    .map(converter::map)
                    .collect(Collectors.toList());

            repository.saveAll(entities);
        } catch (Exception e) {
            log.error("Failed to update groups: {}", groupsForSave, e);
        }
        log.info("Update groups successfully: {}", groupsForSave);
    }

    private Optional<Group> findByNumberInList(List<Group> groups, String number) {
        return groups.stream()
                .filter(el -> Objects.equals(el.getName(), number))
                .findFirst();
    }
}
