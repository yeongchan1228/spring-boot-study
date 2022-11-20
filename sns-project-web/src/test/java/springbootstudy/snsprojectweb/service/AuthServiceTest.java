package springbootstudy.snsprojectweb.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springbootstudy.snsprojectweb.common.ResponseCode;
import springbootstudy.snsprojectweb.common.exception.SnsApplicationException;
import springbootstudy.snsprojectweb.domain.member.entity.Member;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    AuthService authService;

    @Mock
    MemberService memberService;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    @Test
    void 회원가입_정상_작동() {
        String username = "username";
        String password = "password";

        when(memberService.getOptionalMemberByUsername(username)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn("encryptPassword");
        when(memberService.saveMember(any())).thenReturn(mock(Member.class));

        Assertions.assertDoesNotThrow(() -> authService.join(username, password));
    }

    @Test
    void 회원가입_동일_username_사용자가_있을_경우() {
        String username = "username";
        String password = "password";

        when(memberService.getOptionalMemberByUsername(username)).thenThrow(new SnsApplicationException(ResponseCode.DUPLICATED_USERNAME));

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> authService.join(username, password));
        Assertions.assertEquals(ResponseCode.DUPLICATED_USERNAME, e.getResponseCode());
    }

    @Test
    void 로그인_정상_작동() {
        String username = "username";
        String password = "password";

        when(memberService.findByUsername(username)).thenReturn(Member.of(username, password));
        when(passwordEncoder.matches(password, password)).thenReturn(true);
        when(jwtTokenProvider.generateToken(username)).thenReturn("test-token");
        Assertions.assertDoesNotThrow(() -> authService.login(username, password));
    }

    @Test
    void 로그인_회원가입_없이_진행() {
        String username = "username";
        String password = "password";

        when(memberService.findByUsername(username)).thenThrow(new SnsApplicationException(ResponseCode.NOT_FOUND));
        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> authService.login(username, password));
        Assertions.assertEquals(ResponseCode.NOT_FOUND, e.getResponseCode());
    }

    @Test
    void 로그인_패스워드_불일치() {
        String username = "username";
        String password = "password";
        String wrongPassword = "wrongPassword";

        when(memberService.findByUsername(username)).thenReturn(Member.of(username, wrongPassword));

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> authService.login(username, password));
        Assertions.assertEquals(ResponseCode.VALIDATION_ERROR, e.getResponseCode());
    }

}