package study.application.project.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import study.application.project.controller.dto.ApiDataResponse;
import study.application.project.controller.dto.EventResponse;
import study.application.project.domain.constant.EventStatus;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class APIEventController {

//    @ExceptionHandler
//    public ResponseEntity<ApiErrorResponse> general(GeneralException e) {
//        ErrorCode errorCode = e.getErrorCode();
//
//        return ResponseEntity
//                .status(errorCode.getHttpStatus())
//                .body(ApiErrorResponse.of(false, errorCode, errorCode.getMessage(e)));
//    }

    @GetMapping("/events")
    public ApiDataResponse<List<EventResponse>> getEvents() {
        return ApiDataResponse.of(List.of(EventResponse.of(
                1L,
                null,
                "오후 운동",
                EventStatus.OPENED,
                LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                LocalDateTime.of(2021, 1, 1, 16, 0, 0),
                0,
                24,
                "마스크를 꼭 착용하세요"
        )));
    }

    @PostMapping("/events")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiDataResponse<List<EventResponse>> createEvent() {
        return ApiDataResponse.of(List.of(EventResponse.of(
            1L,
            null,
            "오후 운동",
            EventStatus.OPENED,
            LocalDateTime.of(2021, 1, 1, 13, 0, 0),
            LocalDateTime.of(2021, 1, 1, 16, 0, 0),
            0,
            24,
            "마스크를 꼭 착용하세요"
        )));
    }

    @GetMapping("/events/{eventId}")
    public ApiDataResponse<List<EventResponse>> getEvent(@PathVariable Integer eventId) {
        return ApiDataResponse.of(List.of(EventResponse.of(
                1L,
                null,
                "오후 운동",
                EventStatus.OPENED,
                LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                LocalDateTime.of(2021, 1, 1, 16, 0, 0),
                0,
                24,
                "마스크를 꼭 착용하세요"
        )));
    }

    @PutMapping("/events/{eventId}")
    public ApiDataResponse<String> modifyEvent(@PathVariable Integer eventId) { return ApiDataResponse.empty(); }

    @DeleteMapping("/events/{eventId}")
    public ApiDataResponse<String> removeEvent(@PathVariable Integer eventId) { return ApiDataResponse.empty(); }

}
