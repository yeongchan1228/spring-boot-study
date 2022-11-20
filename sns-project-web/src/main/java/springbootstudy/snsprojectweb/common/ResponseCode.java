package springbootstudy.snsprojectweb.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    OK(HttpStatus.OK.value(), HttpStatus.OK, "Ok"),
    CREATED(HttpStatus.CREATED.value(), HttpStatus.CREATED, "Created"),
    DUPLICATED_USERNAME(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, "Username is duplicated."),
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, "Requested Resource Is Not Found"),
    VALIDATION_ERROR(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, "Validation error"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, "Token is invalid."),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, "Permission is invalid."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "BadRequest."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;
}
