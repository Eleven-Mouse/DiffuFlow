package ollamaStar.pojo.dto.respone;

import lombok.Data;

@Data
public class UserLoginResponeDTO {
    Long id;
    private String token;
    private String name;
    private String avatar;
    private Integer vipLevel;
    private Integer standing;
}
