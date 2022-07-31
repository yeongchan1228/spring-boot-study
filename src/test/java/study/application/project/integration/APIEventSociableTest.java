package study.application.project.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.application.project.controller.dto.EventDto;
import study.application.project.service.EventServiceImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Deprecated
@Disabled("Spring Data Rest로 대체")
public class APIEventSociableTest {

    @Autowired private EventServiceImpl eventService;

    @Test
    public void SociableTest() throws Exception {
        // when
//        List<EventDto> events = eventService.getEvents(null, null, null, null, null);

        // then
//        assertThat(events).isEmpty();
    }
}
