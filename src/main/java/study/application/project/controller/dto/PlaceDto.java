package study.application.project.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import study.application.project.domain.Place;
import study.application.project.domain.constant.PlaceType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(staticName = "of")
public class PlaceDto {
    private Long id;
    private PlaceType placeType;
    private String placeName;
    private String address;
    private String phoneNumber;
    private Integer capacity;
    private String memo;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public static PlaceDto of(Place place) {
        return PlaceDto.of(
                place.getId(),
                place.getPlaceType(),
                place.getPlaceName(),
                place.getAddress(),
                place.getPhoneNumber(),
                place.getCapacity(),
                place.getMemo(),
                place.getCreatedAt(),
                place.getModifiedAt()
        );
    }

    public Place toEntity() {
        return Place.of(placeType, placeName, address, phoneNumber, capacity, memo);
    }
}
