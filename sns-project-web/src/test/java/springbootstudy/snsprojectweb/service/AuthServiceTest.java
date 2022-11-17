package springbootstudy.snsprojectweb.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springbootstudy.snsprojectweb.common.ResponseCode;
import springbootstudy.snsprojectweb.common.exception.SnsApplicationException;
import springbootstudy.snsprojectweb.domain.member.entity.Member;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AuthServiceTest {

    AuthService authService;

    @Mock
    MemberService memberService;

    @BeforeEach
    void setAuthService() {
        authService = new AuthService(memberService);
    }

    @Test
    void 회원가입_정상_작동() {
        String username = "username";
        String password = "password";

        when(memberService.getOptionalMemberByUsername(username)).thenReturn(Optional.empty());
        when(memberService.saveMember(any())).thenReturn(mock(Member.class));

        Assertions.assertDoesNotThrow(() -> authService.join(username, password));
    }

    @Test
    void 회원가입_동일_username_사용자가_있을_경우() {
        String username = "username";
        String password = "password";

        when(memberService.getOptionalMemberByUsername(username)).thenThrow(new SnsApplicationException(ResponseCode.DUPLICATED_USERNAME));
        when(memberService.saveMember(any())).thenReturn(mock(Member.class));


        Assertions.assertThrows(SnsApplicationException.class, () -> authService.join(username, password));
    }

    @Test
    void 로그인_정상_작동() {
        String username = "username";
        String password = "password";

        when(memberService.findByUsername(username)).thenReturn(Member.of(username, password));
        Assertions.assertDoesNotThrow(() -> authService.login(username, password));
    }

    @Test
    void 로그인_회원가입_없이_진행() {
        String username = "username";
        String password = "password";

        when(memberService.findByUsername(username)).thenThrow(new SnsApplicationException(ResponseCode.NOT_FOUND));
        Assertions.assertThrows(SnsApplicationException.class, () -> authService.login(username, password));
    }

    @Test
    void 로그인_패스워드_불일치() {
        String username = "username";
        String password = "password";
        String wrongPassword = "wrongPassword";


        when(memberService.findByUsername(username)).thenReturn(Member.of(username, wrongPassword));

        Assertions.assertThrows(SnsApplicationException.class, () -> authService.login(username, password));
    }

}