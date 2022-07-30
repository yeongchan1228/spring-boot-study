package study.application.project.repository;

import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import study.application.project.controller.dto.EventDto;
import study.application.project.domain.Event;
import study.application.project.domain.QEvent;
import study.application.project.domain.constant.EventStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// 인스턴스 생성 편의를 위한 임시 Default 메서드 구현
public interface EventRepository extends
        JpaRepository<Event, Long>,
        QuerydslPredicateExecutor<Event>,
        QuerydslBinderCustomizer<QEvent> {

    @Override
    default void customize(QuerydslBindings bindings, QEvent root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.placeId, root.eventName, root.eventStatus, root.eventStartDatetime, root.eventEndDatetime); // 검색을 하고 싶은 대상
        bindings.bind(root.eventName).first(StringExpression::contains);
        bindings.bind(root.eventStartDatetime).first(ComparableExpression::goe);
        bindings.bind(root.eventEndDatetime).first(ComparableExpression::loe);
    }

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

    default boolean saveEvent(EventDto eventDto) {
        return false;
    }

    default boolean updateEvent(Long eventId, EventDto eventDto) {
        return false;
    }

    default boolean deleteEvent(Long eventId) {
        return false;
    }
}
