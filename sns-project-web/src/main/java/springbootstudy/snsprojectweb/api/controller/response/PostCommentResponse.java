package springbootstudy.snsprojectweb.api.controller.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springbootstudy.snsprojectweb.service.dto.CommentDto;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCommentResponse {
    private Long postId;

    private String content;

    private String username;

    private LocalDateTime createdDate;

    public static PostCommentResponse fromDto(CommentDto commentDto) {
        return PostCommentResponse.of(commentDto.getPostId(), commentDto.getContent(), commentDto.getUsername(), commentDto.getCreatedDate());
    }

    public static PostCommentResponse empty() {
        return new PostCommentResponse();
    }
}
