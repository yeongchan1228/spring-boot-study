package springbootstudy.snsprojectweb.api.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class LoginResponse {
    private String token;
}
