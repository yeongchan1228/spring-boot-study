package springbootstudy.snsprojectweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootstudy.snsprojectweb.domain.alarm.entity.Alarm;
import springbootstudy.snsprojectweb.domain.alarm.repository.AlarmRepository;
import springbootstudy.snsprojectweb.service.dto.AlarmDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmService {

    private final AlarmRepository alarmRepository;

    public Page<AlarmDto> findMyListByMember(String username, Pageable pageable) {
        return alarmRepository.findAllByUsername(username, pageable).map(AlarmDto::fromEntity);
    }

    public Page<AlarmDto> alarmList(String username, Pageable pageable) {
        return findMyListByMember(username, pageable);
    }

    @Transactional
    public void saveAlarm(Alarm alarm) {
        alarmRepository.save(alarm);
    }
}
