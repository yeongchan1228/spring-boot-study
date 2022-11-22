package springbootstudy.snsprojectweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootstudy.snsprojectweb.domain.alarm.entity.Alarm;
import springbootstudy.snsprojectweb.domain.alarm.repository.AlarmRepository;
import springbootstudy.snsprojectweb.domain.member.entity.Member;
import springbootstudy.snsprojectweb.service.dto.AlarmDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmService {

    private final MemberService memberService;

    private final AlarmRepository alarmRepository;

    public Page<AlarmDto> findMyListByMember(Member member, Pageable pageable) {
        return alarmRepository.findAllByToMember(member, pageable).map(AlarmDto::fromEntity);
    }

    public Page<AlarmDto> alarmList(String username, Pageable pageable) {
        Member findMember = memberService.findByUsername(username);
        return findMyListByMember(findMember, pageable);
    }

    @Transactional
    public void saveAlarm(Alarm alarm) {
        alarmRepository.save(alarm);
    }
}
