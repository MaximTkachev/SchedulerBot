package ru.qwerty.schedulerbot.handler.implement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.qwerty.schedulerbot.exception.InternalException;
import ru.qwerty.schedulerbot.service.GroupService;
import ru.qwerty.schedulerbot.service.UserService;
import ru.qwerty.schedulerbot.util.Utils;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class SetGroupHandlerTest {

    private static final String BASE_COMMAND = "/set-group";

    @Mock
    private GroupService groupService;

    @Mock
    private UserService userService;

    private SetGroupHandler handler;

    @BeforeEach
    void setUp() {
        handler = new SetGroupHandler(groupService, userService);
    }

    private static Stream<Arguments> provideArgumentsForTestHandleWithoutGroupNumber() {
        return Stream.of(
                Arguments.of(BASE_COMMAND),
                Arguments.of(BASE_COMMAND + "972001"),
                Arguments.of(BASE_COMMAND + " "),
                Arguments.of(BASE_COMMAND + "    ")
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @MethodSource("provideArgumentsForTestHandleWithoutGroupNumber")
    void testHandleWithoutGroupNumber(String userMessage) {
        Update update = Utils.createUpdateWithText(userMessage);
        assertThatThrownBy(() -> handler.handle(update))
                .isInstanceOf(InternalException.class)
                .hasMessage("Не задан номер группы");
    }

    private static Stream<Arguments> provideArgumentsForTestHandleWithInvalidGroupNumber() {
        return Stream.of(Arguments.of(BASE_COMMAND + " 97200"), Arguments.of(BASE_COMMAND + " 9720011"));
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForTestHandleWithInvalidGroupNumber")
    void testHandleWithInvalidGroupNumber(String userMessage) {
        Update update = Utils.createUpdateWithText(userMessage);
        assertThatThrownBy(() -> handler.handle(update))
                .isInstanceOf(InternalException.class)
                .hasMessage("Некорректный номер группы");
    }

    private static Stream<Arguments> provideArgumentsForTestHandleWithInvalidFaculty() {
        return Stream.of(Arguments.of(BASE_COMMAND + " 962001"), Arguments.of(BASE_COMMAND + " 969999"));
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForTestHandleWithInvalidFaculty")
    void testHandleWithInvalidFaculty(String userMessage) {
        Update update = Utils.createUpdateWithText(userMessage);
        assertThatThrownBy(() -> handler.handle(update))
                .isInstanceOf(InternalException.class)
                .hasMessage("Некорректный номер группы или факультет не поддерживается");
    }

    @AfterEach
    void verifyNoMoreInteractions() {
        Mockito.verifyNoMoreInteractions(groupService, userService);
    }
}