package ollamaStar.server.ollama.client.pojo;

import lombok.Data;

@Data
public class OllamaChatRespone extends OllamaStatisitic {

    private String model;
    private String created_at;
    private OllamaMessage message;

}
