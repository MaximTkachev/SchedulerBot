package ru.qwerty.schedulerbot.core.service.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.qwerty.schedulerbot.core.service.GroupService;
import ru.qwerty.schedulerbot.core.service.MailingService;
import ru.qwerty.schedulerbot.core.service.ScheduleService;
import ru.qwerty.schedulerbot.core.service.UserService;
import ru.qwerty.schedulerbot.data.entity.UserEntity;
import ru.qwerty.schedulerbot.data.model.dto.Schedule;
import ru.qwerty.schedulerbot.i18n.MessageFactory;
import ru.qwerty.schedulerbot.telegram.MessageSender;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultMailingService implements MailingService {

    private static final int BATCH_SIZE = 10;

    private final GroupService groupService;

    private final ScheduleService scheduleService;

    private final UserService userService;

    private final MessageSender messageSender;

    @Override
    public void send() {
        int offset = 0;
        List<UserEntity> users;

        do {
            users = userService.getSubscribed(offset, BATCH_SIZE);
            offset += users.size();

            for (UserEntity user : users) {
                String groupId = groupService.get(user.getGroupNumber()).getId();
                Schedule schedule = scheduleService.get(groupId, new Date());
                messageSender.send(user.getId(), MessageFactory.createSchedule(user.getLanguage(), schedule));
            }
        } while (users.size() == BATCH_SIZE);
    }
}
