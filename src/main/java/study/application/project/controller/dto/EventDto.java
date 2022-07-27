package study.application.project.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import study.application.project.domain.Event;
import study.application.project.domain.constant.EventStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(staticName = "of")
public class EventDto {
    private Long placeId;
    private String eventName;
    private EventStatus eventStatus;
    private LocalDateTime eventStartDatetime;
    private LocalDateTime eventEndDatetime;
    private Integer currentNumberOfPeople;
    private Integer capacity;
    private String mem;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static EventDto of(Event event) {
        return EventDto.of(event.getPlaceId(), event.getEventName(), event.getEventStatus(),
                event.getEventStartDateTime(), event.getEventEndDateTime(), event.getCurrentNumberOfPeople(),
                event.getCapacity(), event.getEventName(), event.getCreatedAt(), event.getModifiedAt());
    }
}
