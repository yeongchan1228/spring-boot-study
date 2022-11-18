package springbootstudy.snsprojectweb.common.exception;

import lombok.Getter;
import org.springframework.util.StringUtils;
import springbootstudy.snsprojectweb.common.ResponseCode;

@Getter
public class SnsApplicationException extends RuntimeException {
    private ResponseCode responseCode;
    private String message;

    public SnsApplicationException(ResponseCode responseCode) {
        this.responseCode = responseCode;
        this.message = null;
    }

    public SnsApplicationException(ResponseCode responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }

    @Override
    public String getMessage() {
        if (!StringUtils.hasText(message)) {
            return responseCode.getMessage();
        }

        return String.format("%s, %s", responseCode.getMessage(), message);
    }
}
