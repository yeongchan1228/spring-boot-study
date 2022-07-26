package study.application.project.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import study.application.project.controller.dto.ApiErrorResponse;
import study.application.project.exception.ErrorCode;
import study.application.project.exception.GeneralException;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice(annotations = RestController.class)
public class ExceptionAPIAdvice {

    @ExceptionHandler
    public ResponseEntity errorJson(GeneralException e) {
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.getHttpStatus().is4xxClientError() ?
                HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(status)
                .body(ApiErrorResponse.of(false, errorCode));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse exception(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;

        return ApiErrorResponse.of(false, errorCode);
    }
}
