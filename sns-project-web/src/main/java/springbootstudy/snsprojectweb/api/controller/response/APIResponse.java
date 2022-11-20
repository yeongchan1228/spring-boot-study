package springbootstudy.snsprojectweb.api.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import springbootstudy.snsprojectweb.common.ResponseCode;

@Getter
@AllArgsConstructor(staticName = "of")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse<T> {
    private int code;
    private String msg;
    private T data;

    public static APIResponse success(ResponseCode responseCode) {
        return APIResponse.of(responseCode.getCode(), responseCode.getMessage(), null);
    }

    public static <T> APIResponse<T> success(ResponseCode responseCode, T data) {
        return APIResponse.of(responseCode.getCode(), responseCode.getMessage(), data);
    }

    public static APIResponse<Void> error(ResponseCode responseCode) {
        return APIResponse.of(responseCode.getCode(), responseCode.getMessage(), null);
    }

    public static APIResponse<Void> error(ResponseCode responseCode, String message) {
        return APIResponse.of(responseCode.getCode(), message, null);
    }
}
