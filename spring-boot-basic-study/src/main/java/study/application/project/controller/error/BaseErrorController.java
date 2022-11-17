package study.application.project.controller.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import study.application.project.controller.dto.ApiErrorResponse;
import study.application.project.exception.ErrorCode;

import javax.servlet.http.HttpServletResponse;

@Controller
public class BaseErrorController implements ErrorController {

    @RequestMapping(value = "/error", produces = MediaType.TEXT_HTML_VALUE)
    public String errorHTML(HttpServletResponse response, Model model) {
        HttpStatus status = HttpStatus.valueOf(response.getStatus());
        ErrorCode errorCode = status.is4xxClientError() ? ErrorCode.BAD_REQUEST : ErrorCode.INTERNAL_ERROR;

        model.addAttribute("statusCode", status);
        model.addAttribute("errorCode", errorCode);
        model.addAttribute("errorMessage", errorCode.getMessage(status.getReasonPhrase()));

        return "/error";
    }

    @RequestMapping("/error")
    public ResponseEntity<ApiErrorResponse> error(HttpServletResponse response) {
        HttpStatus status = HttpStatus.valueOf(response.getStatus());
        ErrorCode errorCode = status.is4xxClientError() ? ErrorCode.BAD_REQUEST : ErrorCode.INTERNAL_ERROR;

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiErrorResponse.of(false, errorCode));
    }

}
