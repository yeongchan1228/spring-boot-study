package study.application.project.controller.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.application.project.exception.ErrorCode;

import static org.assertj.core.api.Assertions.assertThat;


class ApiDataResponseTest {

    @Test
    @DisplayName("문자열 테이터 -> 표준 응답 성공")
    public void givenStringData_whenCreatingResponse_thenReturnsSuccessfulResponse() throws Exception {
        // given
        String data = "Spring Data";

        // when
        ApiDataResponse<String> response = ApiDataResponse.of(data);

        // then
        assertThat(response)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.OK.getCode())
                .hasFieldOrPropertyWithValue("message", ErrorCode.OK.getMessage())
                .hasFieldOrPropertyWithValue("data", data);

    }

    @Test
    @DisplayName("데이터 X -> 표준 응답 성공")
    public void givenNoData_whenCreatingResponse_thenEmptyResponse() throws Exception {
        // given


        // when
        ApiDataResponse<Object> empty = ApiDataResponse.empty();

        // then
        assertThat(empty)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.OK.getCode())
                .hasFieldOrPropertyWithValue("message", ErrorCode.OK.getMessage())
                .hasFieldOrPropertyWithValue("data", null);
    }

}