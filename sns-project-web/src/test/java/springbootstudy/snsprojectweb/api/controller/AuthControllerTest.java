package springbootstudy.snsprojectweb.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import springbootstudy.snsprojectweb.api.controller.request.JoinRequest;
import springbootstudy.snsprojectweb.api.controller.request.LoginRequest;
import springbootstudy.snsprojectweb.common.ResponseCode;
import springbootstudy.snsprojectweb.common.exception.SnsApplicationException;
import springbootstudy.snsprojectweb.service.AuthService;
import springbootstudy.snsprojectweb.service.dto.MemberDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    AuthService authService;

    @Test
    void 회원가입() throws Exception {
        String username = "username";
        String password = "password";

        when(authService.join(any(), any())).thenReturn(MemberDto.empty());

        mockMvc.perform(
                        post("/api/v1/auth/join")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(JoinRequest.of(username, password)))
                )
                .andDo(print())
                .andExpect(status().isCreated());


    }

    @Test
    void 회원가입_이미_존재하는_이메일로_회원가입_시도() throws Exception {
        String username = "username";
        String password = "password";

        when(authService.join(any(), any())).thenThrow(new SnsApplicationException(ResponseCode.DUPLICATED_USERNAME));

        mockMvc.perform(
                        post("/api/v1/auth/join")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(JoinRequest.of(username, password)))
                )
                .andDo(print())
                .andExpect(status().isConflict());

    }

    @Test
    void 로그인() throws Exception {
        String username = "username";
        String password = "password";

        when(authService.login(any(), any())).thenReturn("test-token");

        mockMvc.perform(
                        post("/api/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(LoginRequest.of(username, password)))
                )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void 회원가입_진행하지않은_사용자_로그인() throws Exception {
        String username = "username";
        String password = "password";

        when(authService.login(any(), any())).thenThrow(new SnsApplicationException(ResponseCode.NOT_FOUND));

        mockMvc.perform(
                        post("/api/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(LoginRequest.of(username, password)))
                )
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void 틀린_패스워드_입력_로그인() throws Exception {
        String username = "username";
        String password = "password";

        when(authService.login(any(), any())).thenThrow(new SnsApplicationException(ResponseCode.VALIDATION_ERROR));

        mockMvc.perform(
                        post("/api/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(LoginRequest.of(username, password)))
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}