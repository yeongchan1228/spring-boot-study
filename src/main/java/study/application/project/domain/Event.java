package study.application.project.domain;

import lombok.Data;
import study.application.project.domain.constant.EventStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Event {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long placeId;
    private String eventName;
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;
    private LocalDateTime eventStartDatetime;
    private LocalDateTime eventEndDatetime;
    private Integer currentNumberOfPeople;
    private Integer capacity;
    private String memo;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
