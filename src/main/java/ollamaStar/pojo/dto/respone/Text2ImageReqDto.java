package ollamaStar.pojo.dto.respone;

import lombok.Data;

@Data
public class Text2ImageReqDto
{

    private String pid;
    private long queueIndex = 0;

}
