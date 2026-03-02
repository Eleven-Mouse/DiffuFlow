package ollamaStar.server.service;

import ollamaStar.pojo.MessageBase;
import org.springframework.stereotype.Service;

@Service
public interface ComfyuiMessageService
{
    void handleMessage(MessageBase messageBase);
}
