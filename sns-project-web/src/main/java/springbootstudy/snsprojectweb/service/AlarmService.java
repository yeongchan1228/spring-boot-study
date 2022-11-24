package springbootstudy.snsprojectweb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import springbootstudy.snsprojectweb.cache.repository.MemberCacheRepository;
import springbootstudy.snsprojectweb.common.ResponseCode;
import springbootstudy.snsprojectweb.common.exception.SnsApplicationException;
import springbootstudy.snsprojectweb.domain.alarm.entity.Alarm;
import springbootstudy.snsprojectweb.domain.alarm.repository.AlarmRepository;
import springbootstudy.snsprojectweb.domain.alarm.repository.EmitterRepository;
import springbootstudy.snsprojectweb.domain.member.entity.Member;
import springbootstudy.snsprojectweb.service.dto.AlarmDto;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final MemberCacheRepository memberCacheRepository;
    private final EmitterRepository emitterRepository;

    private final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private final String ALARM_NAME = "alarm";

    public Page<AlarmDto> findMyListByMember(String username, Pageable pageable) {
        return alarmRepository.findAllByUsername(username, pageable).map(AlarmDto::fromEntity);
    }

    public Page<AlarmDto> alarmList(String username, Pageable pageable) {
        return findMyListByMember(username, pageable);
    }

    @Transactional
    public long saveAlarm(Alarm alarm) {
        return alarmRepository.save(alarm).getId();
    }

    public void send(final long alarmId, final String username, final String msg) {
        emitterRepository.get(username).ifPresentOrElse(sseEmitter -> {
            try {
                sseEmitter.send(SseEmitter.event().id(createAlarmId(username, alarmId)).name(ALARM_NAME).data(msg));
            } catch (IOException e) {
                emitterRepository.delete(username);
                throw new SnsApplicationException(ResponseCode.INTERNAL_SERVER_ERROR);
            }
        }, () -> log.info("[SseEmitter] {} SseEmitter Not Founded", username));
    }

    public SseEmitter connectAlarm(String username) {
        Member findMember = memberCacheRepository.getMember(username);

        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(username, sseEmitter);

        // 종료 되었을 때 처리
        sseEmitter.onCompletion(() -> {
            emitterRepository.delete(username);
        });

        // timeOut 시 처리
        sseEmitter.onTimeout(() -> {
            emitterRepository.delete(username);
        });

        try {
            sseEmitter.send(SseEmitter.event().id(createAlarmId(username, null)).name(ALARM_NAME).data("connect completed!!"));
        } catch (IOException e) {
            throw new SnsApplicationException(ResponseCode.ALARM_CONNECT_ERROR);
        }

        return sseEmitter;
    }

    private String createAlarmId(String username, Long alarmId) {
        if (alarmId == null) {
            return username + "_" + System.currentTimeMillis();
        }

        return username + "_" + alarmId;
    }
}
