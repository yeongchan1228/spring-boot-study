package study.application.project.controller.error;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.handler.DispatcherServletWebRequest;
import study.application.project.controller.dto.ApiDataResponse;
import study.application.project.exception.ErrorCode;

import javax.validation.ConstraintViolationException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionAPIAdviceTest {

    private ExceptionAPIAdvice sut;
    private WebRequest request;

    @BeforeEach
    void init() {
        sut = new ExceptionAPIAdvice();
        request = new DispatcherServletWebRequest(new MockHttpServletRequest());
    }

    @Test
    @DisplayName("검증 오류 - 응답")
    public void givenValidationException_whenCallingValidation_thenReturnsResponseEntity() throws Exception {
        // given
        ConstraintViolationException e = new ConstraintViolationException(Set.of());

        // when
        ResponseEntity<Object> response = sut.constraintViolationException(e, request);

        // then
        assertThat(response)
                .hasFieldOrPropertyWithValue("body", ApiDataResponse.of(false, ErrorCode.VALIDATION_ERROR, e))
                .hasFieldOrPropertyWithValue("headers", HttpHeaders.EMPTY)
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("기타 오류")
    public void givenAnyException_whenCallingValidation_thenReturnsResponseEntity() throws Exception {
        // given
        Exception e = new Exception();

        // when
        ResponseEntity<Object> response = sut.exception(e, request);

        // then
        assertThat(response)
                .hasFieldOrPropertyWithValue("body", ApiDataResponse.of(false, ErrorCode.INTERNAL_ERROR, e))
                .hasFieldOrPropertyWithValue("headers", HttpHeaders.EMPTY)
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}