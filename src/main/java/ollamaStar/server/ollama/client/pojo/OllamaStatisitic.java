package ollamaStar.server.ollama.client.pojo;

import lombok.Data;

@Data
public class OllamaStatisitic {

    private boolean done;
    private long totalDuration;
    private long loadDuration;
    private long promptEvalCount;
    private long promptEvalDuration;
    private long evalCount;
    private long evalDuration;
}
