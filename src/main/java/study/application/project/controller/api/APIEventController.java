package study.application.project.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import study.application.project.controller.dto.ApiDataResponse;
import study.application.project.controller.dto.EventRequest;
import study.application.project.controller.dto.EventResponse;
import study.application.project.domain.constant.EventStatus;
import study.application.project.service.EventServiceImpl;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class APIEventController {

    private final EventServiceImpl eventService;
    
//    @ExceptionHandler
//    public ResponseEntity<ApiErrorResponse> general(GeneralException e) {
//        ErrorCode errorCode = e.getErrorCode();
//
//        return ResponseEntity
//                .status(errorCode.getHttpStatus())
//                .body(ApiErrorResponse.of(false, errorCode, errorCode.getMessage(e)));
//    }

    @GetMapping("/events")
    public ApiDataResponse<List<EventResponse>> getEvents(
            @Positive @RequestParam(required = false) Long placeId,
            @Size(min = 2) String eventName,
            EventStatus eventStatus,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventStartDateTime,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventEndDateTime
    ) {
        return ApiDataResponse.of(List.of(EventResponse.of(
                1L,
                "오전 운동",
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
    public ApiDataResponse<String> createEvent(
            @Validated @RequestBody EventRequest eventRequest
            ) {
        return ApiDataResponse.of(Boolean.toString(eventService.saveEvent(eventRequest.toDto())));
    }

    @GetMapping("/events/{eventId}")
    public ApiDataResponse<List<EventResponse>> getEvent(@PathVariable Integer eventId) {
        return ApiDataResponse.of(List.of(EventResponse.of(
                1L,
                "오전 운동",
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
