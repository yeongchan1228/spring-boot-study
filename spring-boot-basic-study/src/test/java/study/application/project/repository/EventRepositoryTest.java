package study.application.project.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import study.application.project.domain.Event;
import study.application.project.domain.constant.EventStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class EventRepositoryTest {

    @Autowired private EventRepository repository;

    @Test
    public void test() throws Exception {
        // given
        LocalDateTime eventStartDatetime = LocalDateTime.of(2021, 1, 1, 9, 0, 0);
        PageRequest pageRequest = PageRequest.of(0, 3);
        Page<Event> page = repository.findPage(1L, null, EventStatus.OPENED, eventStartDatetime, null, pageRequest);

        // when
        List<Event> result = page.getContent();
        long count = page.getTotalElements();

        // then
        assertThat(count).isEqualTo(2);

    }
}