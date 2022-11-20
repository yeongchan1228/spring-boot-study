package springbootstudy.snsprojectweb.api.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import springbootstudy.snsprojectweb.service.dto.PostDto;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(staticName = "of")
public class PostResponse {

    private Long id;

    private String title;

    private String content;

    private LocalDateTime createdDate;

    public static PostResponse fromDto(PostDto postDto) {
        return PostResponse.of(postDto.getId(), postDto.getTitle(), postDto.getContent(), postDto.getCreatedDate());
    }
}
