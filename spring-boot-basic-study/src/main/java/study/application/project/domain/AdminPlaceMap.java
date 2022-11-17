package study.application.project.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminPlaceMap {
    private Long id;

    private Long adminId;
    private Long placedId;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
