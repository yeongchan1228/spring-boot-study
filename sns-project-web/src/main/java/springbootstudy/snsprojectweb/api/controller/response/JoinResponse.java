package springbootstudy.snsprojectweb.api.controller.response;


import lombok.AllArgsConstructor;
import springbootstudy.snsprojectweb.domain.member.entity.Member;
import springbootstudy.snsprojectweb.domain.member.entity.MemberRole;

@AllArgsConstructor(staticName = "of")
public class JoinResponse {
    private Long id;
    private String username;
    private MemberRole role;

    public static JoinResponse fromMember(Member member) {
        return JoinResponse.of(member.getId(), member.getUsername(), member.getRole());
    }
}
