package study.application.project.controller.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import study.application.project.controller.dto.ApiErrorResponse;
import study.application.project.exception.ErrorCode;
import study.application.project.exception.GeneralException;

@RestControllerAdvice(annotations = RestController.class)
public class ExceptionAPIAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> errorJson(GeneralException e, WebRequest request) {
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.getHttpStatus().is4xxClientError() ?
                HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;

//        return ResponseEntity
//                .status(status)
//                .body(ApiErrorResponse.of(false, errorCode));

        return super.handleExceptionInternal(
                e,
                ApiErrorResponse.of(false, errorCode, errorCode.getMessage(e)),
                HttpHeaders.EMPTY,
                status,
                request);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;

        return super.handleExceptionInternal(
                e,
                ApiErrorResponse.of(false, errorCode, errorCode.getMessage(e)),
                HttpHeaders.EMPTY,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorCode errorCode = status.is4xxClientError()
                ? ErrorCode.SPRING_BAD_REQUEST : ErrorCode.SPRING_INTERNAL_ERROR;


        return super.handleExceptionInternal(
                ex,
                ApiErrorResponse.of(false, errorCode, ex),
                headers,
                status,
                request);
    }
}
