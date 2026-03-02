package ollamaStar.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ComfyuiRequestDto {

    @JsonProperty("client_id")
    String clientId;
    Object prompt;

}
