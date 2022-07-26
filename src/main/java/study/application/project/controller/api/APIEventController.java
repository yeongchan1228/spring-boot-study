package study.application.project.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.application.project.controller.dto.ApiErrorResponse;
import study.application.project.exception.ErrorCode;
import study.application.project.exception.GeneralException;

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
    public List<String> getEvents() {
        return List.of("place1", "place2");
    }

    @PostMapping("/events")
    public Boolean createEvent() { return true; }

    @GetMapping("/events/{eventId}")
    public String getEvent(@PathVariable Integer eventId) { return "event " + eventId;}

    @PutMapping("/events/{eventId}")
    public Boolean modifyEvent(@PathVariable Integer eventId) { return true; }

    @DeleteMapping("/events/{eventId}")
    public Boolean removeEvent(@PathVariable Integer eventId) { return true; }

}
