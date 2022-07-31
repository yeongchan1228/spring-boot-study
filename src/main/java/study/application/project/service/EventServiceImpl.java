package study.application.project.service;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.application.project.controller.dto.EventDto;
import study.application.project.domain.constant.EventStatus;
import study.application.project.exception.ErrorCode;
import study.application.project.exception.GeneralException;
import study.application.project.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    List<EventDto> getEvents(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime
    ) {
        return List.of();
    }

    public List<EventDto> getEvents(Predicate predicate) {

        try {
            return StreamSupport.stream(eventRepository.findAll(predicate).spliterator(), false)
                    .map(EventDto::of)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public Optional<EventDto> getEvent(Long eventId) {
        try {
            return eventRepository.findById(eventId).map(EventDto::of);
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public boolean saveEvent(EventDto eventDto) {
        if (eventDto == null) return false;

        try {
            eventRepository.save(eventDto.toEntity());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }

        return true;
    }

    public boolean modifyEvent(Long eventId, EventDto eventDto) {
        if (eventDto == null || eventId == null) return false;

        try {
            eventRepository.findById(eventId).ifPresent(event -> eventRepository.save(eventDto.updateEntity(event)));
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }

        return true;
    }

    public boolean deleteEvent(Long eventId) {
        try {
            eventRepository.findById(eventId).ifPresent(event -> eventRepository.delete(event));
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }

        return true;
    }
}
