package ollamaStar.server.service;

import java.util.HashMap;
import java.util.List;

public interface WsNoticeService
{
    void sendProgress(String promptId, HashMap<String, Object> data);

    void sendExecuted(String promptId, List<HashMap<String, Object>>
            images);
}
