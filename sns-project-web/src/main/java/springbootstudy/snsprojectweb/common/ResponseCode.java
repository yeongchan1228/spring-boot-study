package springbootstudy.snsprojectweb.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    OK(HttpStatus.OK, "Success"),
    CREATED(HttpStatus.CREATED, "Success Created"),
    DUPLICATED_USERNAME(HttpStatus.CONFLICT, "Username is duplicated"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Requested Resource Is Not Found"),
    VALIDATION_ERROR(HttpStatus.UNAUTHORIZED, "Validation error"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Token is invalid"),
    INVALID_PERMISSION(HttpStatus.FORBIDDEN, "Permission is invalid"),
    ALREADY_LIKED(HttpStatus.CONFLICT, "Member already liked the post"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BadRequest"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");

    private final HttpStatus httpStatus;
    private final String message;
}
