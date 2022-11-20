package springbootstudy.snsprojectweb.service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springbootstudy.snsprojectweb.domain.post.entity.Post;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDto {

    private Long id;

    private String title;

    private String content;

    private LocalDateTime createdDate;

    public static PostDto fromEntity(Post post) {
        return PostDto.of(post.getId(), post.getTitle(), post.getContent(), post.getCreatedDate());
    }
}
