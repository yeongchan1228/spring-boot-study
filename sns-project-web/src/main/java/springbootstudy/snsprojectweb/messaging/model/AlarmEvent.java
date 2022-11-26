package springbootstudy.snsprojectweb.messaging.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springbootstudy.snsprojectweb.domain.alarm.entity.AlarmType;
import springbootstudy.snsprojectweb.domain.member.entity.Member;
import springbootstudy.snsprojectweb.domain.post.entity.Post;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class AlarmEvent {
    private AlarmType alarmType;

    private Member fromMember;

    private Post targetPost;

    private String msg;
}
