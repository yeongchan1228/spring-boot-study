package study.application.project.repository.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;
import study.application.project.domain.Event;
import study.application.project.domain.QEvent;
import study.application.project.domain.constant.EventStatus;

import java.time.LocalDateTime;
import java.util.List;

import static study.application.project.domain.QEvent.*;

public class EventRepositoryImpl extends QuerydslRepositorySupport implements EventRepositoryCustom {

    public EventRepositoryImpl() {
        super(Event.class);
    }

    @Override
    public Page<Event> findPage(Long placeId,
                                String eventName,
                                EventStatus eventStatus,
                                LocalDateTime eventStartDatetime,
                                LocalDateTime eventEndDatetime,
                                Pageable pageable) {
        JPQLQuery<Event> query =
                from(event)
                        .select(event);
        query.where(isPlaceId(placeId),
                isEventName(eventName),
                isEventStatus(eventStatus),
                isEventStartDatetime(eventStartDatetime),
                isEventEndDatetime(eventEndDatetime)
        );

        QueryResults result = getQuerydsl()
                .applyPagination(pageable, query).fetchResults();
//        query.offset(pageable.getOffset())
//                .limit(pageable.getPageSize());

//        if (placeId != null) {
//            query.where(event.placeId.eq(placeId));
//        }

//        PageableExecutionUtils.getPage()

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    BooleanExpression isPlaceId(Long placeId) {
        return placeId != null ? event.placeId.eq(placeId) : null;
    }

    BooleanExpression isEventName(String eventName) {
        return StringUtils.hasText(eventName) ? event.eventName.eq(eventName) : null;
    }

    BooleanExpression isEventStatus(EventStatus eventStatus) {
        return eventStatus != null ? event.eventStatus.eq(eventStatus) : null;
    }

    BooleanExpression isEventStartDatetime(LocalDateTime eventStartDatetime) {
        return eventStartDatetime != null ? event.eventStartDatetime.goe(eventStartDatetime) : null;
    }

    BooleanExpression isEventEndDatetime(LocalDateTime eventEndDatetime) {
        return eventEndDatetime != null ? event.eventEndDatetime.loe(eventEndDatetime) : null;
    }

}
