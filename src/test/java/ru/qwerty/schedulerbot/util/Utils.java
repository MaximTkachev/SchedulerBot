package ru.qwerty.schedulerbot.util;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Utils {

    public static Update createUpdateWithText(String text) {
        Message message = new Message();
        message.setText(text);
        Update update = new Update();
        update.setMessage(message);
        return update;
    }
}
