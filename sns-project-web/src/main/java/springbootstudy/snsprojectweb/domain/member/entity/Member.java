package springbootstudy.snsprojectweb.domain.member.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import springbootstudy.snsprojectweb.domain.common.BaseEntity;
import springbootstudy.snsprojectweb.domain.post.entity.Post;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@DynamicUpdate
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private MemberRole role = MemberRole.ROLE_USER;

    @OneToMany
    private List<Post> posts = new ArrayList<>();

    @Builder(builderMethodName = "createMember")
    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static Member of(String username, String password) {
        return Member.createMember()
                .username(username)
                .password(password)
                .build();
    }

    public static Member empty() {
        return Member.createMember()
                .build();
    }
}
