package study.application.project.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import study.application.project.domain.constant.EventStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class EventRequest {
    @Positive @NotNull
    private Long placeId;
    @NotBlank
    private String eventName;
    @NotNull
    private EventStatus eventStatus;
    @NotNull
    private LocalDateTime eventStartDateTime;
    @NotNull
    private LocalDateTime eventEndDateTime;
    @NotNull @PositiveOrZero
    private Integer currentNumberOfPeople;
    @NotNull @Positive
    private Integer capacity;
    private String memo;

    public EventDto toDto() {
        return EventDto.of(
                placeId,
                eventName,
                eventStatus,
                eventStartDateTime,
                eventEndDateTime,
                currentNumberOfPeople,
                capacity,
                memo,
                null,
                null
        );
    }
}
