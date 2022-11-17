package study.application.project.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import study.application.project.exception.ErrorCode;
import study.application.project.exception.GeneralException;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler
    public String errorHTML(GeneralException e, Model model) {
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.getHttpStatus().is4xxClientError() ?
                HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;

        model.addAttribute("statusCode", status);
        model.addAttribute("errorCode", errorCode);
        model.addAttribute("errorMessage", errorCode.getMessage(status.getReasonPhrase()));

        return "/error";
    }

    @ExceptionHandler
    public String exception(Exception e, Model model) {
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        HttpStatus status = errorCode.getHttpStatus();

        model.addAttribute("statusCode", status);
        model.addAttribute("errorCode", errorCode);
        model.addAttribute("errorMessage", errorCode.getMessage(status.getReasonPhrase()));

        return "/error";
    }
}
