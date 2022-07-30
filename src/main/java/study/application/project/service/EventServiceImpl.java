package study.application.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.application.project.controller.dto.EventDto;
import study.application.project.domain.constant.EventStatus;
import study.application.project.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public List<EventDto> getEvents(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime
    ) {
        return eventRepository.findEvents(placeId, eventName, eventStatus, eventStartDateTime, eventEndDateTime);
    }

    public Optional<EventDto> getEvent(Long eventId) {
        return eventRepository.findEvent(eventId);
    }

    public boolean createEvent(EventDto eventDto) {
        return eventRepository.saveEvent(eventDto);
    }

    public boolean modifyEvent(Long eventId, EventDto eventDto) {
        return eventRepository.updateEvent(eventId, eventDto);
    }

    public boolean deleteEvent(Long eventId) {
        return eventRepository.deleteEvent(eventId);
    }
}
