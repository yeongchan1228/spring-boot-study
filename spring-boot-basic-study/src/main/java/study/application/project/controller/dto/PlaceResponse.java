package study.application.project.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import study.application.project.domain.constant.PlaceType;

@Data
@AllArgsConstructor(staticName = "of")
public class PlaceResponse {
    private PlaceType placeType;
    private String placeName;
    private String address;
    private String phoneNumber;
    private Integer capacity;
    private String memo;

    public static PlaceResponse from(PlaceDto placeDto) {
        if(placeDto == null) return null;

        return PlaceResponse.of(
                placeDto.getPlaceType(),
                placeDto.getPlaceName(),
                placeDto.getAddress(),
                placeDto.getPhoneNumber(),
                placeDto.getCapacity(),
                placeDto.getMemo()
        );
    }
}
