package ollamaStar.pojo.dto.request;

import lombok.Data;

@Data
public class Text2ImageReqDto {
    int size;
    int model;
    int scale;
    int cfg;
    int sampler;
    int seed;
    String reverse;
    String propmt;
    String clientId;
}
