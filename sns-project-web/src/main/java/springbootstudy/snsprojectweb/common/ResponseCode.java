package springbootstudy.snsprojectweb.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    OK(HttpStatus.OK.value(), HttpStatus.OK, "OK"),
    DUPLICATED_USERNAME(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, "Username is duplicated."),
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, "Requested Resource Is Not Found"),
    VALIDATION_ERROR(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, "Validation error"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;
}
