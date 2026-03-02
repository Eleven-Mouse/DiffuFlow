package ollamaStar.pojo.dto.common;

import cn.hutool.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result<T> {
    protected static final String REQUEST_OK = "ok";
    private String code;
    private String msg;
    private T data;

    public static Result<Void> ok() {
        return new Result<Void>(HttpStatus.HTTP_OK+"", REQUEST_OK, null);
    }

    public static <T> Result<T> ok(T body) {
        return new Result(HttpStatus.HTTP_OK+"", REQUEST_OK, body);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(HttpStatus.HTTP_BAD_REQUEST+"", msg, null);
    }

    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code+"", msg, null);
    }
    public static <T> Result<T> failed(ResultCode resultCode) {
        return  new Result<>(resultCode.getCode(), resultCode.getMsg(), null);
    }

}
