package springbootstudy.snsprojectweb.api.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class LoginRequest {
    private String username;
    private String password;
}
