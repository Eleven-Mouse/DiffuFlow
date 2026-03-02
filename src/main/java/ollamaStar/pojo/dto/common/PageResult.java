package ollamaStar.pojo.dto.common;

import cn.hutool.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> extends Result<T> {
    private long total;

    public static PageResult ok(long total, Object data) {
        PageResult tPageResult = new PageResult<>();
        tPageResult.setCode(HttpStatus.HTTP_OK+"");
        tPageResult.setMsg(REQUEST_OK);
        tPageResult.setTotal(total);
        tPageResult.setData(data);
        return tPageResult;
    }

}
