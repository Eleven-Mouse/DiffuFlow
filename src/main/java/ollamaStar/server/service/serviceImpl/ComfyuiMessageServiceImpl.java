package ollamaStar.server.service.serviceImpl;

import ollamaStar.pojo.MessageBase;
import ollamaStar.server.service.WsNoticeService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ComfyuiMessageServiceImpl
{
    @Autowired
    WsNoticeService wsNoticeService;

    public void handleMessage(MessageBase messageBase) {
        if("progress".equals(messageBase.getType())){
            handleProgress(messageBase);
        }else if("executed".equals(messageBase.getType())){
            handleExecuted(messageBase);
        }

    }

    private void handleProgress(MessageBase messageBase) {
        HashMap<String, Object> data = messageBase.getData();
        String promptId = (String) data.get("prompt_id"); // 任务ID
        wsNoticeService.sendProgress(promptId,data);
    }
    private void handleExecuted(MessageBase messageBase) {
        HashMap<String, Object> data = messageBase.getData();
        // 任务ID
        String promptId = (String) data.get("prompt_id");
        HashMap<String, Object> output = (HashMap<String, Object>) data.get("output");
        List<HashMap<String, Object>> images = (List<HashMap<String, Object>>) output.get("images");
        wsNoticeService.sendExecuted(promptId,images);
    }

}
