package ollamaStar.server.service.serviceImpl;

import com.alibaba.fastjson2.JSON;
import ollamaStar.pojo.ComfyuiTask;
import ollamaStar.server.service.RedisService;
import ollamaStar.server.service.WsNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class WsNoticeServiceImpl implements WsNoticeService
{
    public final static String COMFYUI_QUEUE_TOPIC = "/topic/messages";

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    RedisService redisService;

    private void sendToUser(String wsClientId, String message){
        simpMessagingTemplate.convertAndSendToUser(wsClientId,COMFYUI_QUEUE_TOPIC,message);
    }

    private void sendToAll(String message){
        simpMessagingTemplate.convertAndSend( COMFYUI_QUEUE_TOPIC, message);

    }

    @Override
    public void sendProgress(String promptId, HashMap<String, Object> data) {
        ComfyuiTask task = redisService.getStartedTask(promptId);
        if(task!=null) {
            data.put("type","progress");
            sendToUser(task.getWsClientId(), JSON.toJSONString(data));
        }
    }


    @Override
    public void sendExecuted(String promptId, List<HashMap<String, Object>>
            images) {
        ComfyuiTask task = redisService.getStartedTask(promptId);
        if(task!=null) {
            // 记录生成结果
            List<String> urls = new ArrayList<>();
            for (HashMap image : images) {
                urls.add( String.format("http://192.168.100.129:8188/view? filename=%s&type=%s&&subfolder=%s",image.get("filename"),image.get("type"),
                image.get("subfolder")));
            }
            HashMap<String, Object> temp = new HashMap<>();
            temp.put("type","imageResult");
            temp.put("urls",urls);
            sendToUser(task.getWsClientId(), JSON.toJSONString(temp));
        }
    }

}
