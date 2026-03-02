package ollamaStar.server.ollama.client.pojo;

import lombok.Data;

@Data
public class OllamaMessage {

    private String role;
    private String content;
    private String images;

}
