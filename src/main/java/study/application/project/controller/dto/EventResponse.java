package study.application.project.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import study.application.project.domain.constant.EventStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(staticName = "of")
public class EventResponse {
    private Long placeId;
    private String eventName;
    private EventStatus eventStatus;
    private LocalDateTime eventStartDatetime;
    private LocalDateTime eventEndDatetime;
    private Integer currentNumberOfPeople;
    private Integer capacity;
    private String mem;

    public static EventResponse empty(PlaceDto placeDto) {
        return EventResponse.of(null, null, null, null, null, null, null, null);
    }

    public static EventResponse from(EventDto eventDto) {
        if (eventDto == null) return null;

        return EventResponse.of(
                eventDto.getPlaceId(),
                eventDto.getEventName(),
                eventDto.getEventStatus(),
                eventDto.getEventStartDatetime(),
                eventDto.getEventEndDatetime(),
                eventDto.getCurrentNumberOfPeople(),
                eventDto.getCapacity(),
                eventDto.getMem()
        );
    }

}
