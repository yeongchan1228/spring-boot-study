package springbootstudy.snsprojectweb.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springbootstudy.snsprojectweb.api.controller.request.PostRequest;
import springbootstudy.snsprojectweb.api.controller.response.APIResponse;
import springbootstudy.snsprojectweb.service.PostService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public APIResponse create(@RequestBody PostRequest postRequest, Authentication authentication) {
        return APIResponse.success(postService.create(postRequest.getTitle(), postRequest.getContent(), authentication.getName()));
    }
}
