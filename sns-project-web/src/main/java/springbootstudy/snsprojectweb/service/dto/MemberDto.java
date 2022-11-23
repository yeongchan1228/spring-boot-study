package springbootstudy.snsprojectweb.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springbootstudy.snsprojectweb.domain.member.entity.Member;
import springbootstudy.snsprojectweb.domain.member.entity.MemberRole;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberDto {
    private Long id;
    private String username;
    private String password;
    @JsonIgnore
    private MemberRole role;

    public static MemberDto fromEntity(Member member) {
        return MemberDto.of(member.getId(), member.getUsername(), member.getPassword(), member.getRole());
    }

    public static MemberDto empty() {
        return new MemberDto();
    }
}
