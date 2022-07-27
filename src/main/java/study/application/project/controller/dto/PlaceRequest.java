package study.application.project.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import study.application.project.domain.constant.PlaceType;

@Data
@AllArgsConstructor(staticName = "of")
public class PlaceRequest {
    private Long id;
    private PlaceType placeType;
    private String placeName;
    private String address;
    private String phoneNumber;
    private Integer capacity;
    private String memo;
}
