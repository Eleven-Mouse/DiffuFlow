package ollamaStar.pojo.dto.respone;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Text2ImageResDto
{

    private String clientId = "";

    // 图片比例 1=1:1 2=3:4 3=4:3
    private int scale = 1;
    // 图片张数
    @Max(message = "最多生成4张",value = 4)
    private int size = 1;
    // 步数
    private int step = 20;
    // 强度
    private int cfg = 7;
    // 采样计划 1=DPM++ SDE Karras 2=DPM++ 2S Karras 3=Euler Karras 4=DPM++ 3M SDE Karras
    private int sampler = 1;
    // 随机种子
    @Min(message = "最小值为0",value = 0)
    private long seed = 0;
    // 正向词
    @NotBlank(message = "请输入提示词")
    private String propmt = "";
    // 负向词
    private String reverse = "";
    // 模型
    private int model = 1;

    public String modelName(){
        switch (model){
            case 2:
                return "anythingelseV4_v45.safetensors";
            default:
                return "majicmixRealistic_v7.safetensors";
        }
    }

    public String samplerName(){
        switch (sampler){
            case 1:
                return "dpmpp_sde";
            case 2:
                return "dpmpp_2m";
            case 3:
                return "euler";
            case 4:
                return "dpmpp_3m_sde";
            default:
                return "euler";
        }
    }

    public String scheduler(){
        return "karras";
    }


    public int width() {
        if(scale==3){
            return 768;
        } else {
            return 512;
        }
    }

    public int height() {
        if(scale==2){
            return 768;
        } else {
            return 512;
        }
    }

}
