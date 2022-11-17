package study.application.project.exception;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.*;

class ErrorCodeTest {

    @MethodSource
    @ParameterizedTest(name = "[{index}] \"{0}\" ====> \"{1}\"")
    @DisplayName("예외를 받으면 예외에 포함된 메시지 출력")
    public void givenException_whenGettingMessage_thenReturnsMessage(ErrorCode input, String expected) throws Exception {
        // given
        Exception e = new Exception("Test Message");

        // when
        String actual = input.getMessage(e);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> givenException_whenGettingMessage_thenReturnsMessage() {
        return Stream.of(
                arguments(ErrorCode.OK, "Ok - Test Message"),
                arguments(ErrorCode.BAD_REQUEST, "Bad request - Test Message"),
                arguments(ErrorCode.SPRING_BAD_REQUEST, "Spring-detected bad request - Test Message"),
                arguments(ErrorCode.INTERNAL_ERROR, "Internal error - Test Message"),
                arguments(ErrorCode.SPRING_INTERNAL_ERROR, "Spring-detected internal error - Test Message"),
                arguments(ErrorCode.VALIDATION_ERROR, "Validation error - Test Message")
        );
    }

    @MethodSource
    @ParameterizedTest(name = "[{index}] \"{0}\" ====> \"{1}\"")
    @DisplayName("메시지를 받으면 해당 메시지로 출력")
    public void givenMessage_whenGettingMessage_thenReturnsMessage(String input, String expected) throws Exception {
        // given

        // when
        String actual = ErrorCode.INTERNAL_ERROR.getMessage(input);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> givenMessage_whenGettingMessage_thenReturnsMessage() {
        return Stream.of(
            arguments(null, ErrorCode.INTERNAL_ERROR.getMessage()),
            arguments("", ErrorCode.INTERNAL_ERROR.getMessage()),
            arguments("   ", ErrorCode.INTERNAL_ERROR.getMessage()),
            arguments(null, ErrorCode.INTERNAL_ERROR.getMessage()),
            arguments("Test Message", "Test Message")
        );
    }
}