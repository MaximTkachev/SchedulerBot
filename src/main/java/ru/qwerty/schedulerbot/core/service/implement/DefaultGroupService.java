package ru.qwerty.schedulerbot.core.service.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.qwerty.schedulerbot.client.TsuInTimeClient;
import ru.qwerty.schedulerbot.core.repository.GroupRepository;
import ru.qwerty.schedulerbot.core.service.GroupService;
import ru.qwerty.schedulerbot.core.util.Validator;
import ru.qwerty.schedulerbot.data.converter.GroupMapper;
import ru.qwerty.schedulerbot.data.entity.GroupEntity;
import ru.qwerty.schedulerbot.data.model.dto.Group;
import ru.qwerty.schedulerbot.exception.InternalException;
import ru.qwerty.schedulerbot.exception.ObjectNotFoundException;
import ru.qwerty.schedulerbot.exception.TimeoutException;
import ru.qwerty.schedulerbot.i18n.MessageKey;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The default implementation of the {@link GroupService} interface.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultGroupService implements GroupService {

    private final GroupMapper groupMapper;

    private final GroupRepository groupRepository;

    private final TsuInTimeClient tsuInTimeClient;

    @Override
    @Transactional
    public GroupEntity get(String number) {
        log.info("Get group: number = {}", number);
        Validator.checkGroupNumber(number);

        GroupEntity groupEntity = groupRepository.findByNumber(number);
        if (groupEntity != null) {
            return groupEntity;
        }
        return getGroupFromServer(number);
    }

    private synchronized GroupEntity getGroupFromServer(String number) {
        GroupEntity groupEntity = groupRepository.findByNumber(number);
        if (groupEntity != null) {
            return groupEntity;
        }

        List<Group> groups = fetchGroupsFromServer();
        updateGroups(groups);

        groupEntity = groupRepository.findByNumber(number);
        if (groupEntity != null) {
            return groupEntity;
        }

        throw new ObjectNotFoundException(MessageKey.GROUP_NOT_FOUND);
    }

    private List<Group> fetchGroupsFromServer() {
        try {
            return tsuInTimeClient.getGroups();
        } catch (TimeoutException e) {
            throw new ObjectNotFoundException(MessageKey.GROUP_NOT_FOUND);
        } catch (Exception e) {
            throw new InternalException();
        }
    }

    private void updateGroups(List<Group> groups) {
        List<Group> groupsForSave = new ArrayList<>();
        for (Group group : groups) {
            if (groupRepository.findByNumber(group.getName()) == null) {
                groupsForSave.add(group);
            }
        }

        log.info("Update groups: {}", groupsForSave);
        try {
            List<GroupEntity> entities = groupsForSave.stream()
                    .map(groupMapper::map)
                    .collect(Collectors.toList());

            groupRepository.saveAll(entities);
        } catch (Exception e) {
            log.error("Failed to update groups: {}", groupsForSave, e);
        }
        log.info("Update groups successfully: {}", groupsForSave);
    }
}
