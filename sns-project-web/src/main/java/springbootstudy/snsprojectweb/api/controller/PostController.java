package springbootstudy.snsprojectweb.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springbootstudy.snsprojectweb.api.controller.request.PostRequest;
import springbootstudy.snsprojectweb.api.controller.response.APIResponse;
import springbootstudy.snsprojectweb.api.controller.response.PostResponse;
import springbootstudy.snsprojectweb.common.ResponseCode;
import springbootstudy.snsprojectweb.service.PostService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public APIResponse createPost(@RequestBody PostRequest postRequest, Authentication authentication) {
        postService.create(postRequest.getTitle(), postRequest.getContent(), authentication.getName());
        return APIResponse.success(ResponseCode.CREATED);
    }

    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public APIResponse modifyPost(@RequestBody PostRequest postRequest, @PathVariable Long postId, Authentication authentication) {
        if (postId == null) {
            return APIResponse.error(ResponseCode.BAD_REQUEST, "postId is null");
        }

        return APIResponse.success(
                ResponseCode.OK,
                PostResponse.fromDto(
                        postService.modify(postRequest.getTitle(), postRequest.getContent(), authentication.getName(), postId)
                )
        );
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public APIResponse deletePost(@PathVariable Long postId, Authentication authentication) {
        if (postId == null) {
            return APIResponse.error(ResponseCode.BAD_REQUEST, "postId is null");
        }

        postService.delete(authentication.getName(), postId);
        return APIResponse.success(ResponseCode.OK);
    }
}
