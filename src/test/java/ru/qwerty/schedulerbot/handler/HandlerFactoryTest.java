package ru.qwerty.schedulerbot.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.qwerty.schedulerbot.converter.UserConverter;
import ru.qwerty.schedulerbot.handler.implement.DefaultHandler;
import ru.qwerty.schedulerbot.handler.implement.ErrorHandler;
import ru.qwerty.schedulerbot.handler.implement.GetCurrentGroupHandler;
import ru.qwerty.schedulerbot.handler.implement.GetMenuHandler;
import ru.qwerty.schedulerbot.handler.implement.GetScheduleHandler;
import ru.qwerty.schedulerbot.handler.implement.SetGroupHandler;
import ru.qwerty.schedulerbot.handler.implement.StartHandler;
import ru.qwerty.schedulerbot.handler.implement.SubscribeHandler;
import ru.qwerty.schedulerbot.handler.implement.UnsubscribeHandler;
import ru.qwerty.schedulerbot.service.UserService;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class HandlerFactoryTest {

    @Mock
    private UserConverter userConverter;

    @Mock
    private UserService userService;

    private HandlerFactory factory;

    @BeforeEach
    void setUp() {
        factory = new HandlerFactory(userConverter, userService);
    }

    private static Stream<Arguments> provideArgumentsForTestCreateWithGetCurrentGroupCommand() {
        return Stream.of(
                Arguments.of(createUpdate("/get-group")),
                Arguments.of(createUpdate("/get-group ")),
                Arguments.of(createUpdate("/get-group     ")),
                Arguments.of(createUpdate("/get-group 123"))
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForTestCreateWithGetCurrentGroupCommand")
    void testCreateWithGetCurrentGroupCommand(Update update) {
        Handler handler = factory.create(update);
        assertThat(handler).isInstanceOf(GetCurrentGroupHandler.class);
    }

    private static Stream<Arguments> provideArgumentsForTestCreateWithGetMenuCommand() {
        return Stream.of(
                Arguments.of(createUpdate("/menu")),
                Arguments.of(createUpdate("/menu ")),
                Arguments.of(createUpdate("/menu     ")),
                Arguments.of(createUpdate("/menu 123"))
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForTestCreateWithGetMenuCommand")
    void testCreateWithGetMenuCommand(Update update) {
        Handler handler = factory.create(update);
        assertThat(handler).isInstanceOf(GetMenuHandler.class);
    }

    private static Stream<Arguments> provideArgumentsForTestCreateWithGetScheduleCommand() {
        return Stream.of(
                Arguments.of(createUpdate("/get")),
                Arguments.of(createUpdate("/get ")),
                Arguments.of(createUpdate("/get     ")),
                Arguments.of(createUpdate("/get 123"))
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForTestCreateWithGetScheduleCommand")
    void testCreateWithGetScheduleCommand(Update update) {
        Handler handler = factory.create(update);
        assertThat(handler).isInstanceOf(GetScheduleHandler.class);
    }

    private static Stream<Arguments> provideArgumentsForTestCreateWithSetGroupCommand() {
        return Stream.of(
                Arguments.of(createUpdate("/set-group")),
                Arguments.of(createUpdate("/set-group ")),
                Arguments.of(createUpdate("/set-group     ")),
                Arguments.of(createUpdate("/set-group 123"))
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForTestCreateWithSetGroupCommand")
    void testCreateWithSetGroupCommand(Update update) {
        Handler handler = factory.create(update);
        assertThat(handler).isInstanceOf(SetGroupHandler.class);
    }

    private static Stream<Arguments> provideArgumentsForTestCreateWithStartCommand() {
        return Stream.of(
                Arguments.of(createUpdate("/start")),
                Arguments.of(createUpdate("/start ")),
                Arguments.of(createUpdate("/start     ")),
                Arguments.of(createUpdate("/start 123"))
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForTestCreateWithStartCommand")
    void testCreateWithStartCommand(Update update) {
        Handler handler = factory.create(update);
        assertThat(handler).isInstanceOf(StartHandler.class);
    }

    private static Stream<Arguments> provideArgumentsForTestCreateWithSubscribeCommand() {
        return Stream.of(
                Arguments.of(createUpdate("/subscribe")),
                Arguments.of(createUpdate("/subscribe ")),
                Arguments.of(createUpdate("/subscribe     ")),
                Arguments.of(createUpdate("/subscribe 123"))
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForTestCreateWithSubscribeCommand")
    void testCreateWithSubscribeCommand(Update update) {
        Handler handler = factory.create(update);
        assertThat(handler).isInstanceOf(SubscribeHandler.class);
    }

    private static Stream<Arguments> provideArgumentsForTestCreateWithUnsubscribeCommand() {
        return Stream.of(
                Arguments.of(createUpdate("/unsubscribe")),
                Arguments.of(createUpdate("/unsubscribe ")),
                Arguments.of(createUpdate("/unsubscribe     ")),
                Arguments.of(createUpdate("/unsubscribe 123"))
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForTestCreateWithUnsubscribeCommand")
    void testCreateWithUnsubscribeCommand(Update update) {
        Handler handler = factory.create(update);
        assertThat(handler).isInstanceOf(UnsubscribeHandler.class);
    }

    private static Stream<Arguments> provideArgumentsForTestCreateWithInvalidData() {
        return Stream.of(
                Arguments.of(new Update()),
                Arguments.of(createUpdate(null)),
                Arguments.of(createUpdate(""))
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForTestCreateWithInvalidData")
    void testCreateWithInvalidData(Update update) {
        Handler handler = factory.create(update);
        assertThat(handler).isInstanceOf(ErrorHandler.class);
    }

    private static Stream<Arguments> provideArgumentsForTestCreateWithInvalidCommand() {
        return Stream.of(
                Arguments.of(createUpdate("  ")),
                Arguments.of(createUpdate("  /get-group")),
                Arguments.of(createUpdate("/")),
                Arguments.of(createUpdate("/set"))
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForTestCreateWithInvalidCommand")
    void testCreateWithInvalidCommand(Update update) {
        Handler handler = factory.create(update);
        assertThat(handler).isInstanceOf(DefaultHandler.class);
    }

    private static Update createUpdate(String text) {
        Message message = new Message();
        message.setText(text);
        Update update = new Update();
        update.setMessage(message);
        return update;
    }
}
