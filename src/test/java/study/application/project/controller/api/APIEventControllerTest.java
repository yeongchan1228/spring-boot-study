package study.application.project.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import study.application.project.controller.dto.EventDto;
import study.application.project.controller.dto.EventRequest;
import study.application.project.controller.dto.EventResponse;
import study.application.project.domain.constant.EventStatus;
import study.application.project.exception.ErrorCode;
import study.application.project.service.EventServiceImpl;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Deprecated
@Disabled("Spring Data Rest로 대체")
@WebMvcTest(APIEventController.class)
class APIEventControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper mapper;

    @MockBean private EventServiceImpl eventService;

    public APIEventControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
        this.mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("[API][GET] 이벤트 리스트 조회")
    void givenParam_whenRequestingEvents_thenReturnsListOfEvents() throws Exception {

        // given
//        given(eventService.getEvents(any(), any(), any(), any(), any()))
//                .willReturn(List.of(createEventDto()));
//
//        // when & then
//        mvc.perform(get("/api/events")
//                        .queryParam("placeId", "1")
//                        .queryParam("eventName", "운동")
//                        .queryParam("eventStatus", EventStatus.OPENED.name())
//                        .queryParam("eventStartDateTime", "2021-01-01T00:00:00")
//                        .queryParam("eventEndDateTime", "2021-01-01T00:00:00")
//                )
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.data[0].placeId").value(1L))
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
//                .andDo(print());
//
//        then(eventService).should().getEvents(any(), any(), any(), any(), any());
    }

    @Test
    @DisplayName("[API][GET] 이벤트 리스트 잘못된 조회")
    void givenWrongParam_whenRequestingEvents_thenReturnsFailed() throws Exception {

        // when & then
        mvc.perform(get("/api/events")
                        .queryParam("placeId", "-1")
                        .queryParam("eventName", "운")
                        .queryParam("eventStatus", EventStatus.OPENED.name())
                        .queryParam("eventStartDateTime", "2021-01-01T00:00:00")
                        .queryParam("eventEndDateTime", "2021-01-01T00:00:00")
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.VALIDATION_ERROR.getCode()))
                .andExpect(jsonPath("$.message").value(containsString(ErrorCode.VALIDATION_ERROR.getMessage())))
                .andDo(print());
    }


    @Test
    @DisplayName("[API][POST] 이벤트 생성")
    void testEventResponse() throws Exception {
        EventRequest eventRequest = EventRequest.of(
                1L,
                "오후 운동",
                EventStatus.OPENED,
                null,
                null,
                0,
                24,
                "마스크를 꼭 착용하세요"
        );

        mvc.perform(
                post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(eventRequest))
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andDo(print());
    }

    @Test
    @DisplayName("[API][POST] 잘못된 요청")
    void testEventResponse_Validation() throws Exception {
        EventResponse eventResponse = EventResponse.of(
                -1L,
                "   ",
                null,
                LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                LocalDateTime.of(2021, 1, 1, 16, 0, 0),
                -1,
                0,
                "마스크를 꼭 착용하세요"
        );

//        mvc.perform(
//                        post("/api/events")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(mapper.writeValueAsString(eventResponse))
//                )
//                .andExpect(status().isBadRequest())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.success").value(false))
//                .andExpect(jsonPath("$.errorCode").value(ErrorCode.VALIDATION_ERROR.getCode()))
//                .andExpect(jsonPath("$.message").value(containsString(ErrorCode.VALIDATION_ERROR.getMessage())))
//                .andDo(print());
    }

    private EventDto createEventDto() {
        return EventDto.of(
                1L,
                "오후 운동",
                EventStatus.OPENED,
                LocalDateTime.of(2021, 1, 1, 13, 0 , 0),
                LocalDateTime.of(2021, 1, 1, 16, 0 , 0),
                0,
                24,
                "마스크를 꼭 착용하세요.",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}