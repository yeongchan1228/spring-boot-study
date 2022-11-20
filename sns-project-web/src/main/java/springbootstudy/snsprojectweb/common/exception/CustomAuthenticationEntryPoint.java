package springbootstudy.snsprojectweb.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import springbootstudy.snsprojectweb.api.controller.response.APIResponse;
import springbootstudy.snsprojectweb.common.ResponseCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();

        response.setContentType("application/json");
        response.setStatus(ResponseCode.INVALID_TOKEN.getCode());
        response.getWriter().write(objectMapper.writeValueAsString(APIResponse.error(ResponseCode.INVALID_TOKEN)));
    }
}
