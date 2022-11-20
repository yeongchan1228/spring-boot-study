package springbootstudy.snsprojectweb.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springbootstudy.snsprojectweb.api.controller.request.JoinRequest;
import springbootstudy.snsprojectweb.api.controller.request.LoginRequest;
import springbootstudy.snsprojectweb.api.controller.response.APIResponse;
import springbootstudy.snsprojectweb.api.controller.response.LoginResponse;
import springbootstudy.snsprojectweb.common.ResponseCode;
import springbootstudy.snsprojectweb.service.AuthService;
import springbootstudy.snsprojectweb.service.dto.MemberDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * 회원가입
     */
    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public APIResponse join(@RequestBody JoinRequest joinRequest) {
        MemberDto memberDto = authService.join(joinRequest.getUsername(), joinRequest.getPassword());
        return APIResponse.success(
                ResponseCode.OK,
                memberDto
        );
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public APIResponse login(@RequestBody LoginRequest loginRequest) {
        return APIResponse.success(
                ResponseCode.OK,
                LoginResponse.of(authService.login(loginRequest.getUsername(), loginRequest.getPassword()))
        );
    }
}
