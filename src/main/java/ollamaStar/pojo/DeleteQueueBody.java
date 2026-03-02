package ollamaStar.pojo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DeleteQueueBody {
    List<String> delete;
}
