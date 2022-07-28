package study.application.project.controller.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import study.application.project.controller.dto.ApiErrorResponse;
import study.application.project.exception.ErrorCode;
import study.application.project.exception.GeneralException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice(annotations = RestController.class)
public class ExceptionAPIAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> generalException(GeneralException e, WebRequest request) {

//        return ResponseEntity
//                .status(status)
//                .body(ApiErrorResponse.of(false, errorCode));

        return getInternalResponseEntity(e, e.getErrorCode(), HttpHeaders.EMPTY,
                e.getErrorCode().getHttpStatus().is4xxClientError() ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> constraintViolationException(ConstraintViolationException e, WebRequest request) {

        return getInternalResponseEntity(e,
                ErrorCode.VALIDATION_ERROR, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {

        return getInternalResponseEntity(e,
                ErrorCode.INTERNAL_ERROR, HttpHeaders.EMPTY, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


    private ResponseEntity<Object> getInternalResponseEntity(Exception e, ErrorCode errorCode, HttpHeaders headers, HttpStatus status,  WebRequest request) {
        return super.handleExceptionInternal(
                e,
                ApiErrorResponse.of(false, errorCode, errorCode.getMessage(e)),
                headers,
                status,
                request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorCode errorCode = null;
        if (ex instanceof MethodArgumentNotValidException) {
            errorCode = ErrorCode.VALIDATION_ERROR;
        } else {
            errorCode = status.is4xxClientError()
                    ? ErrorCode.SPRING_BAD_REQUEST : ErrorCode.SPRING_INTERNAL_ERROR;
        }
        return super.handleExceptionInternal(
                ex,
                ApiErrorResponse.of(false, errorCode, ex),
                headers,
                status,
                request);
    }

}
