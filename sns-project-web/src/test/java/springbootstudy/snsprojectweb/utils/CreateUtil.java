package springbootstudy.snsprojectweb.utils;

import springbootstudy.snsprojectweb.domain.alarm.entity.Alarm;
import springbootstudy.snsprojectweb.domain.alarm.entity.AlarmType;
import springbootstudy.snsprojectweb.domain.member.entity.Member;
import springbootstudy.snsprojectweb.domain.post.entity.Post;

public class CreateUtil {

    public static Member createMember(String username) {
        return Member.of(username, null);
    }

    public static Post createPost(long postId, Member member) {
        return Post.of(postId, null, null, member);
    }

    public static Post createPost(long postId) {
        return Post.of(postId, null, null, null);
    }

    public static Alarm createAlarm(AlarmType alarmType, Member fromMember, Member toMember, Post targetPost) {
        return Alarm.of(alarmType, fromMember, toMember, targetPost);
    }
}
