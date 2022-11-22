package springbootstudy.snsprojectweb.api.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import springbootstudy.snsprojectweb.service.dto.AlarmDto;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(staticName = "of")
public class AlarmResponse {

    private Long id;
    private long fromMemberId;
    private long targetPostId;
    private String alarmType;
    private String text;
    private LocalDateTime createdDate;

    public static AlarmResponse fromDto(AlarmDto alarmDto) {
        return AlarmResponse.of(alarmDto.getId(), alarmDto.getFromMember().getId(), alarmDto.getTargetPost().getId(),
                alarmDto.getAlarmType().toString(), alarmDto.getAlarmType().getContent(), alarmDto.getCreatedDate());
    }

}
