package ollamaStar.pojo.dto.common;

import lombok.Data;

@Data
public class PageRequestDto {

    private Integer pageNum;
    private Integer pageSize;

    public void checkPage()
    {
        if(pageNum==null||pageNum<=0)
        {
            pageNum = 1;
        }
        if(pageSize==null||pageSize<=0)
        {
            pageSize = 150;
        }
        if(pageSize>200){
            pageSize = 150;
        }
    }
}
