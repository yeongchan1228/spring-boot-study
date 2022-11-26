package springbootstudy.snsprojectweb.messaging.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import springbootstudy.snsprojectweb.messaging.model.AlarmEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmProducer {

    private final KafkaTemplate<Long, AlarmEvent> kafkaTemplate;

    @Value("${spring.kafka.topic.alarm}")
    private String alarmTopicName;

    public void send(AlarmEvent alarmEvent) {
        kafkaTemplate.send(alarmTopicName, alarmEvent.getTargetPost().getMember().getId(), alarmEvent);
        log.info("Finished send.");
    }
}
