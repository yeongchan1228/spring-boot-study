package springbootstudy.snsprojectweb.domain.alarm.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springbootstudy.snsprojectweb.domain.common.BaseEntity;
import springbootstudy.snsprojectweb.domain.member.entity.Member;
import springbootstudy.snsprojectweb.domain.post.entity.Post;

import javax.persistence.*;

@Getter
@Entity
@Table(indexes = {@Index(name = "to_member_id_idx", columnList = "to_member_id")})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_member_id")
    private Member fromMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_member_id")
    private Member toMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post targetPost;

    @Builder(builderMethodName = "createAlarm")
    public Alarm(Long id, AlarmType alarmType, Member fromMember, Member toMember, Post targetPost) {
        this.id = id;
        this.alarmType = alarmType;
        this.fromMember = fromMember;
        this.toMember = toMember;
        this.targetPost = targetPost;
    }

    public static Alarm of(AlarmType alarmType, Member fromMember, Member toMember, Post targetPost) {
        return Alarm.createAlarm()
                .alarmType(alarmType)
                .fromMember(fromMember)
                .toMember(toMember)
                .targetPost(targetPost)
                .build();
    }
}
