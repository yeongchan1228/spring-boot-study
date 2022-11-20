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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder(builderMethodName = "createPost")
    public Post(Long id, String title, String content, Member member) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public static Post of(String title, String content) {
        return Post.createPost()
                .title(title)
                .content(content)
                .member(null)
                .build();
    }

    public static Post of(String title, String content, Member member) {
        return Post.createPost()
                .title(title)
                .content(content)
                .member(member)
                .build();
    }

    public static Post of(long id, String title, String content, Member member) {
        return Post.createPost()
                .id(id)
                .title(title)
                .content(content)
                .member(member)
                .build();
    }

    public void modifyPost(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
