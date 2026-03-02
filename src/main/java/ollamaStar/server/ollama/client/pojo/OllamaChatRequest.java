package ollamaStar.server.ollama.client.pojo;

import lombok.Data;

import java.util.List;

@Data
public class OllamaChatRequest {

    private String model;
    private String format;
    private boolean stream;
    private String keepAlive="5m";
    private OllamaChatOption options;
    private List<OllamaMessage> messages;

}
