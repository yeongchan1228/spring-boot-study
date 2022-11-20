package springbootstudy.snsprojectweb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootstudy.snsprojectweb.common.ResponseCode;
import springbootstudy.snsprojectweb.common.exception.SnsApplicationException;
import springbootstudy.snsprojectweb.domain.member.entity.Member;
import springbootstudy.snsprojectweb.service.dto.MemberDto;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberService memberService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public MemberDto join(String username, String password) {
        // username duplicate check
        checkDuplicateUsername(username);

        return MemberDto.fromEntity(memberService.saveMember(Member.of(username, passwordEncoder.encode(password))));
    }

    public String login(String username, String password) {
        // 회원 가입 여부 확인
        Member member = memberService.findByUsername(username);

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new SnsApplicationException(ResponseCode.VALIDATION_ERROR, "Password is invalid.");
        }

        return jwtTokenProvider.generateToken(username);
    }

    private void checkDuplicateUsername(String username) {
        memberService.getOptionalMemberByUsername(username)
                .ifPresent(it -> {
                    throw new SnsApplicationException(
                            ResponseCode.DUPLICATED_USERNAME,
                            String.format("%s is duplicated.", username)
                    );
                });
    }
}
