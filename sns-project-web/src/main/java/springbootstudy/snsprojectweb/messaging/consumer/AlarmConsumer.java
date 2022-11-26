package springbootstudy.snsprojectweb.messaging.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import springbootstudy.snsprojectweb.messaging.model.AlarmEvent;
import springbootstudy.snsprojectweb.service.AlarmService;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmConsumer {

    private final ObjectMapper objectMapper;
    private final AlarmService alarmService;

    @KafkaListener(topics = "${spring.kafka.topic.alarm}")
    public void consumeAlarm(AlarmEvent alarmEvent, Acknowledgment ack) {
        try {
            log.info("Consume the event {}", objectMapper.writeValueAsString(alarmEvent));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        alarmService.send(alarmEvent.getAlarmType(), alarmEvent.getFromMember(), alarmEvent.getTargetPost(), alarmEvent.getMsg());
    }
}
