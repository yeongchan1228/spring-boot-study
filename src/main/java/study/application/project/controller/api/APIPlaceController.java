package study.application.project.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import study.application.project.controller.dto.ApiDataResponse;
import study.application.project.controller.dto.PlaceRequest;
import study.application.project.controller.dto.PlaceResponse;
import study.application.project.domain.constant.PlaceType;

import java.util.List;

@RestController
@RequestMapping("/api")
public class APIPlaceController {

    @GetMapping("/places")
    public ApiDataResponse<List<PlaceResponse>> getPlaces() {
        return ApiDataResponse.of(List.of(PlaceResponse.of(
                PlaceType.COMMON,
                "partyRoom",
                "강남",
                "010-4156-5974",
                20,
                "qwrqwrqwr"
        )));
    }

    @PostMapping("/places")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiDataResponse<String> createPlace() {
        return ApiDataResponse.empty();
    }

    @GetMapping("/places/{placeId}")
    public  ApiDataResponse<Void> getPlace(@PathVariable Integer placeId) {
        return ApiDataResponse.empty();
    }

    @PutMapping("/places/{placeId}")
    public ApiDataResponse<Void> modifyPlace(
            @PathVariable Integer placeId,
            @RequestBody PlaceRequest placeRequest) {
        return ApiDataResponse.empty();
    }

    @DeleteMapping("/places/{placeId}")
    public ApiDataResponse<Void> removePlace(@PathVariable Integer placeId) {
        return ApiDataResponse.empty();
    }

}
