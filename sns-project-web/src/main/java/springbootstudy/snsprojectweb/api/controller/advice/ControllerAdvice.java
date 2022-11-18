package springbootstudy.snsprojectweb.api.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import springbootstudy.snsprojectweb.api.controller.response.APIResponse;
import springbootstudy.snsprojectweb.common.ResponseCode;
import springbootstudy.snsprojectweb.common.exception.SnsApplicationException;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(SnsApplicationException.class)
    public ResponseEntity snsApplicationHandler(SnsApplicationException e) {
        log.error("[SnsApplicationException]", e);
        return ResponseEntity.status(e.getResponseCode().getHttpStatus())
                .body(APIResponse.error(e.getResponseCode(), e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity runtimeHandler(RuntimeException e) {
        log.error("[SnsApplicationException]", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(APIResponse.error(ResponseCode.INTERNAL_SERVER_ERROR));
    }
}
