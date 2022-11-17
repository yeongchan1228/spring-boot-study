package study.application.project.controller.functional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RequestPredicates.*;
import static org.springframework.web.servlet.function.RouterFunctions.*;

//@Configuration
public class APIPlaceRouter {

//    @Bean
//    public RouterFunction<ServerResponse> placeRouter() {
//        return route()
//                .GET("/api/places", req -> ServerResponse.ok().body(List.of("place1", "place2")))
//                .POST("/api/places", req -> ServerResponse.ok().body(true))
//                .GET("/api/places/{placeId}", req -> ServerResponse.ok().body("place " + req.pathVariable("placeId")))
//                .PUT("/api/places/{placeId}", req -> ServerResponse.ok().body(true))
//                .DELETE("/api/places/{placeId}", req -> ServerResponse.ok().body(true))
//                .build();
//    }

//    @Bean
//    public RouterFunction<ServerResponse> placeRouter() {
//        return route().nest(path("/api/places"), builder -> builder
//            .GET("", req -> ServerResponse.ok().body(List.of("place1", "place2")))
//            .POST("", req -> ServerResponse.ok().body(true))
//            .GET("/{placeId}", req -> ServerResponse.ok().body("place " + req.pathVariable("placeId")))
//            .PUT("/{placeId}", req -> ServerResponse.ok().body(true))
//            .DELETE("/{placeId}", req -> ServerResponse.ok().body(true))
//            )
//        .build();
//    }

    @Bean
    public RouterFunction<ServerResponse> placeRouter(APIPlaceHandler apiPlaceHandler) {
        return route().nest(path("/api/places"), builder -> builder
                .GET("", apiPlaceHandler::getPlaces)
                .POST("", apiPlaceHandler::createPlaces)
                .GET("/{placeId}", apiPlaceHandler::getPlace)
                .PUT("/{placeId}", apiPlaceHandler::modifyPlace)
                .DELETE("/{placeId}", apiPlaceHandler::removePlace)
        ).build();
    }
}
