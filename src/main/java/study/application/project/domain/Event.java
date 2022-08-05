package study.application.project.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import study.application.project.domain.constant.EventStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(columnList = "placeId"),
        @Index(columnList = "eventName"),
})
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

    @CreatedDate
    @Column(updatable = false, nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime modifiedAt;

    protected Event(Long placeId, String eventName, EventStatus eventStatus, LocalDateTime eventStartDatetime, LocalDateTime eventEndDatetime, Integer currentNumberOfPeople, Integer capacity, String memo) {
        this.placeId = placeId;
        this.eventName = eventName;
        this.eventStatus = eventStatus;
        this.eventStartDatetime = eventStartDatetime;
        this.eventEndDatetime = eventEndDatetime;
        this.currentNumberOfPeople = currentNumberOfPeople;
        this.capacity = capacity;
        this.memo = memo;
    }

    public static Event of(Long placeId, String eventName, EventStatus eventStatus, LocalDateTime eventStartDatetime, LocalDateTime eventEndDatetime, Integer currentNumberOfPeople, Integer capacity, String memo) {
        return new Event(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime, currentNumberOfPeople, capacity, memo);
    }

}
