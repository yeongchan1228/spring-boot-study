package springbootstudy.snsprojectweb.service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springbootstudy.snsprojectweb.domain.alarm.entity.Alarm;
import springbootstudy.snsprojectweb.domain.alarm.entity.AlarmType;
import springbootstudy.snsprojectweb.domain.member.entity.Member;
import springbootstudy.snsprojectweb.domain.post.entity.Post;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AlarmDto {

    private Long id;
    private Member fromMember;
    private Post targetPost;
    private AlarmType alarmType;
    private LocalDateTime createdDate;

    public static AlarmDto fromEntity(Alarm alarm) {
        return AlarmDto.of(alarm.getId(), alarm.getFromMember(), alarm.getTargetPost(), alarm.getAlarmType(), alarm.getCreatedDate());
    }

    public static AlarmDto empty() {
        return new AlarmDto();
    }
}
