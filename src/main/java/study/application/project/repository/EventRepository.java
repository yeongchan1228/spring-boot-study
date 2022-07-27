package study.application.project.repository;

import study.application.project.controller.dto.EventDto;
import study.application.project.domain.constant.EventStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// TODO: 인스턴스 생성 편의를 위한 임시 Default 메서드 구현
public interface EventRepository {
    default List<EventDto> findEvents(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime
    ) {
        return List.of();
    }

    default Optional<EventDto> findEvent(Long eventId) {
        return Optional.empty();
    }

    default boolean save(EventDto eventDto) {
        return false;
    }

    default boolean update(Long eventId, EventDto eventDto) {
        return false;
    }

    default boolean delete(Long eventId) {
        return false;
    }
}
