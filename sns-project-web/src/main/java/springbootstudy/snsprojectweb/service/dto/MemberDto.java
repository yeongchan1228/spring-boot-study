package springbootstudy.snsprojectweb.service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springbootstudy.snsprojectweb.domain.member.entity.Member;
import springbootstudy.snsprojectweb.domain.member.entity.MemberRole;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDto {
    private Long id;
    private String username;
    private String password;
    private MemberRole role;

    public static MemberDto fromEntity(Member member) {
        return MemberDto.of(member.getId(), member.getUsername(), member.getPassword(), member.getRole());
    }

    public static MemberDto empty() {
        return new MemberDto();
    }
}
