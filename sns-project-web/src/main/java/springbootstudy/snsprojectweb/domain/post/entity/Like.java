package springbootstudy.snsprojectweb.domain.post.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springbootstudy.snsprojectweb.domain.common.BaseEntity;
import springbootstudy.snsprojectweb.domain.member.entity.Member;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "\"like\"")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder(builderMethodName = "createLike")
    public Like(Long id, Post post, Member member) {
        this.id = id;
        this.post = post;
        this.member = member;
    }

    public static Like of(Post post, Member member) {
        return Like.createLike()
                .post(post)
                .member(member)
                .build();
    }
}
