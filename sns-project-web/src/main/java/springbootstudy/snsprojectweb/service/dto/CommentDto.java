package springbootstudy.snsprojectweb.service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springbootstudy.snsprojectweb.domain.post.entity.Comment;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentDto {
    private Long postId;

    private String content;

    private String username;

    private LocalDateTime createdDate;

    public static CommentDto fromEntity(Comment comment) {
        return CommentDto.of(comment.getPost().getId(), comment.getContent(), comment.getMember().getUsername(), comment.getCreatedDate());
    }

    public static CommentDto empty() {
        return new CommentDto();
    }

}
