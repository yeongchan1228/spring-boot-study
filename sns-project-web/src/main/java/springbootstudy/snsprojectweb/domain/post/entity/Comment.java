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
@Table(indexes = {@Index(name = "post_id_idx", columnList = "post_id")})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder(builderMethodName = "createComment")
    public Comment(Long id, String content, Post post, Member member) {
        this.id = id;
        this.content = content;
        this.post = post;
        this.member = member;
    }

    public static Comment of(String content, Post post, Member member) {
        return Comment.createComment()
                .content(content)
                .post(post)
                .member(member)
                .build();
    }
}
