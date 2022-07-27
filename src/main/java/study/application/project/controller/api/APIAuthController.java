package study.application.project.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.application.project.controller.dto.ApiDataResponse;

@RestController
@RequestMapping("/api")
public class APIAuthController {

    @GetMapping("/sign-up")
    public ApiDataResponse<String> signUp() {
        return ApiDataResponse.empty();
    }

    @GetMapping("/login")
    public ApiDataResponse<String> login() {
        return ApiDataResponse.empty();
    }
}
