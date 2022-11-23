package springbootstudy.snsprojectweb.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import springbootstudy.snsprojectweb.domain.alarm.entity.Alarm;
import springbootstudy.snsprojectweb.domain.alarm.entity.AlarmType;
import springbootstudy.snsprojectweb.domain.alarm.repository.AlarmRepository;
import springbootstudy.snsprojectweb.domain.member.entity.Member;
import springbootstudy.snsprojectweb.domain.post.entity.Post;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static springbootstudy.snsprojectweb.utils.CreateUtil.*;

@ExtendWith(MockitoExtension.class)
class AlarmServiceTest {

    @InjectMocks
    AlarmService alarmService;

    @Mock
    AlarmRepository alarmRepository;

    @Mock
    MemberService memberService;

    @Test
    void 알람_리스트_조회_정상_작동() throws Exception {
        // given
        Member member = createMember("username");
        Pageable pageable = mock(Pageable.class);

        // when
        when(alarmRepository.findAllByUsername(member.getUsername(), pageable)).thenReturn(Page.empty());

        // then
        assertDoesNotThrow(() -> alarmService.alarmList(member.getUsername(), pageable));
    }

    @Test
    void 알람_저장_정상_작동() throws Exception {
        // given
        Member toMember = createMember("username1");
        Member fromMember = createMember("username2");
        Post post = createPost(1, toMember);
        Alarm alarm = createAlarm(AlarmType.NEW_COMMENT_ON_POST, fromMember, post.getMember(), post);

        // when
        when(alarmRepository.save(alarm)).thenReturn(any(Alarm.class));

        // then
        assertDoesNotThrow(() -> alarmService.saveAlarm(alarm));
    }

}