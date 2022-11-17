package study.application.project.repository.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.application.project.domain.Event;
import study.application.project.domain.constant.EventStatus;

import java.time.LocalDateTime;

public interface EventRepositoryCustom {

    Page<Event> findPage(Long placeId, String eventName, EventStatus eventStatus,
                         LocalDateTime eventStartDatetime, LocalDateTime eventEndDatetime, Pageable pageable);

}
