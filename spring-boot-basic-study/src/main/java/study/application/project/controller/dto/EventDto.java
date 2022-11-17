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
    private String memo;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static EventDto of(Event event) {
        return EventDto.of(event.getPlaceId(), event.getEventName(), event.getEventStatus(),
                event.getEventStartDatetime(), event.getEventEndDatetime(), event.getCurrentNumberOfPeople(),
                event.getCapacity(), event.getEventName(), event.getCreatedAt(), event.getModifiedAt());
    }

    public Event toEntity() {
        return Event.of(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime, currentNumberOfPeople, capacity, memo);
    }

    public Event updateEntity(Event event) {
        if (eventName != null) { event.setEventName(eventName); }
        if (eventStatus != null) { event.setEventStatus(eventStatus); }
        if (eventStartDatetime != null) { event.setEventStartDatetime(eventStartDatetime); }
        if (eventEndDatetime != null) { event.setEventEndDatetime(eventEndDatetime); }
        if (currentNumberOfPeople != null) { event.setCurrentNumberOfPeople(currentNumberOfPeople); }
        if (capacity != null) { event.setCapacity(capacity); }
        if (memo != null) { event.setMemo(memo); }

        return event;
    }
}
