package study.application.project.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import study.application.project.controller.dto.EventDto;
import study.application.project.domain.constant.EventStatus;
import study.application.project.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class) // Mockito Mock을 사용하기 위하
class EventServiceImplTest {

    @Mock private EventRepository eventRepository; // @Mock의 인스턴스를 @InjectMocks가 붙은 인스턴스에 주입힌다.
    @InjectMocks private EventServiceImpl eventService;

    @Test
    @DisplayName("검색 조건 없이 조회 -> 전체 목록")
    public void givenNoting_whenSearchingEvents_thenReturnsEventList() throws Exception {
        // given
        given(eventRepository.findEvents(null, null, null, null, null))
                .willReturn(List.of(
                        createEventDto(1L, "오전 운동", true),
                        createEventDto(1L, "오후 운동", true)
                ));

        // when
        List<EventDto> list = eventService.getEvents(null, null, null, null, null);

        // then
        assertThat(list).hasSize(2);
        then(eventRepository).should().findEvents(null, null, null, null, null);
    }

    @Test
    @DisplayName("검색 조건으로 조회 -> 검색 조회 목록")
    public void givenInfo_whenSearchingEvents_thenReturnsEventList() throws Exception {
        // given
        Long placeId = 1L;
        String eventName = "오후 운동";
        EventStatus eventStatus = EventStatus.OPENED;
        LocalDateTime eventStartDateTime = LocalDateTime.of(2021, 1, 1, 13, 0, 0);
        LocalDateTime eventEndDateTime = LocalDateTime.of(2021, 1, 1, 16, 0, 0);

        given(eventRepository.findEvents(placeId, eventName, eventStatus, eventStartDateTime, eventEndDateTime))
                .willReturn(List.of(
                        createEventDto(1L, "오후 운동", eventStatus, eventStartDateTime, eventEndDateTime)
                ));

        // when
        List<EventDto> list = eventService.getEvents(placeId, eventName, eventStatus, eventStartDateTime, eventEndDateTime);

        // then
        assertThat(list)
                .hasSize(1)
                .allSatisfy(event -> {
                    assertThat(event)
                            .hasFieldOrPropertyWithValue("placeId", placeId)
                            .hasFieldOrPropertyWithValue("eventName", eventName)
                            .hasFieldOrPropertyWithValue("eventStatus", eventStatus);
                    assertThat(event.getEventStartDatetime()).isAfterOrEqualTo(eventStartDateTime);
                    assertThat(event.getEventStartDatetime()).isBeforeOrEqualTo(eventEndDateTime);
                });
        then(eventRepository).should().findEvents(placeId, eventName, eventStatus, eventStartDateTime, eventEndDateTime);
    }


    @Test
    @DisplayName("이벤트 Id 조회 -> 해당 이벤트 출력")
    public void givenEventId_WhenSearchingExistingEvent_thenReturnsEvent() throws Exception {
        // given
        Long eventId = 1L;
        EventDto eventDto = createEventDto(1L, "오전 운동", true);
        given(eventRepository.findEvent(eventId)).willReturn(Optional.of(eventDto));

        // when
        Optional<EventDto> result = eventService.getEvent(eventId);

        // then
        assertThat(result).hasValue(eventDto);
        then(eventRepository).should().findEvent(eventId);
    }

    @Test
    @DisplayName("이벤트 Id 조회 -> 빈 정보 이벤트 출력")
    public void givenEventId_WhenSearchingExistingEvent_thenReturnsEmpty() throws Exception {
        // given
        Long eventId = 2L;
        given(eventRepository.findEvent(eventId)).willReturn(Optional.empty());

        // when
        Optional<EventDto> result = eventService.getEvent(eventId);

        // then
        assertThat(result).isEmpty();
        then(eventRepository).should().findEvent(eventId);

    }

    @Test
    @DisplayName("이벤트 생성 -> true")
    public void givenEvent_WhenCreatingEvent_thenReturnsTrue() throws Exception {
        // given
        EventDto dto = createEventDto(1L, "오후 운동", false);
        given(eventRepository.save(dto)).willReturn(true);

        // when
        boolean result = eventService.createEvent(dto);

        // then
        assertThat(result).isTrue();
        then(eventRepository).should().save(dto);
    }

    @Test
    @DisplayName("이벤트 수정 -> true")
    public void givenEventId_WhenModifying_thenReturnsTrue() throws Exception {
        // given
        Long eventId = 2L;
        EventDto dto = createEventDto(1L, "오후 운동", false);
        given(eventRepository.update(eventId, dto)).willReturn(true);

        // when
        boolean modifyEvent = eventService.modifyEvent(eventId, dto);

        // then
        assertThat(modifyEvent).isTrue();
        then(eventRepository).should().update(eventId, dto);
    }

    @Test
    @DisplayName("이벤트 수정 -> false")
    public void givenNothing_WhenModifying_thenReturnsFalse() throws Exception {
        // given
        EventDto dto = createEventDto(1L, "오후 운동", false);
        given(eventRepository.update(null, dto)).willReturn(false);

        // when
        boolean modifyEvent = eventService.modifyEvent(null, dto);

        // then
        assertThat(modifyEvent).isFalse();
        then(eventRepository).should().update(null, dto);
    }

    @Test
    @DisplayName("이벤트 수정 -> false")
    public void givenEventIdWithNothing_WhenModifying_thenReturnsFalse() throws Exception {
        // given
        Long eventId = 1L;
        given(eventRepository.update(eventId, null)).willReturn(false);

        // when
        boolean modifyEvent = eventService.modifyEvent(eventId, null);

        // then
        assertThat(modifyEvent).isFalse();
        then(eventRepository).should().update(eventId, null);

    }

    @Test
    @DisplayName("이벤트 삭제 -> true")
    public void givenEventId_WhenDeleting_thenReturnsTrue() throws Exception {
        // given
        Long eventId = 1L;
        given(eventRepository.delete(eventId)).willReturn(true);

        // when
        boolean modifyEvent = eventService.deleteEvent(eventId);

        // then
        assertThat(modifyEvent).isTrue();
        then(eventRepository).should().delete(eventId);
    }

    @Test
    @DisplayName("이벤트 삭제 -> false")
    public void givenNothing_WhenDeleting_thenReturnsFalse() throws Exception {
        // given
        given(eventRepository.delete(null)).willReturn(false);

        // when
        boolean modifyEvent = eventService.deleteEvent(null);

        // then
        assertThat(modifyEvent).isFalse();
        then(eventRepository).should().delete(null);
    }

    private EventDto createEventDto(
            long placeId,
            String eventName,
            Boolean isMorning
    ) {
        String hourStart = isMorning ? "09" : "13";
        String hourEnd = isMorning ? "12" : "16";

        return createEventDto(
                placeId,
                eventName,
                EventStatus.OPENED,
                LocalDateTime.parse(String.format("2021-01-01T%s:00:00", hourStart)),
                LocalDateTime.parse(String.format("2021-01-01T%s:00:00", hourEnd))
        );
    }

    private EventDto createEventDto(
            long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime
    ) {
        return EventDto.of(placeId, eventName, eventStatus, eventStartDateTime,
                eventEndDateTime, 0, 24, "마스크를 꼭 착용하세요",
                LocalDateTime.now(), LocalDateTime.now());
    }

}