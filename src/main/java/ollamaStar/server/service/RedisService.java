package ollamaStar.server.service;

import ollamaStar.pojo.ComfyuiTask;
import org.springframework.stereotype.Service;

@Service
public interface RedisService
{
    ComfyuiTask addQueueTask(ComfyuiTask comfyuiTask);

    ComfyuiTask popQueueTask();

    void addStartedTask(String promptId, ComfyuiTask task);

    ComfyuiTask getStartedTask(String promptId);
}
