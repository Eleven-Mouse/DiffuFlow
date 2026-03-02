package ollamaStar.server.ollama.client.pojo;

import lombok.Data;

import java.util.List;

@Data
public class OllamaChatOption {

    private long seed;
    private int topK;
    private float topP;
    private int repeatLastN;
    private float temperature;
    private float repeatPenalty;
    private List<String> stop;


}
