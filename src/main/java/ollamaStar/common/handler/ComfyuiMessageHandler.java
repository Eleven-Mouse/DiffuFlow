package ollamaStar.common.handler;

import com.alibaba.fastjson2.JSON;
import ollamaStar.pojo.MessageBase;
import ollamaStar.server.service.ComfyuiMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

//消息处理类
public class ComfyuiMessageHandler extends TextWebSocketHandler {
    @Autowired
    ComfyuiMessageService comfyuiMessageService;
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("=============连接成功");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage
            message) throws Exception {
        System.out.println("=============收到消息:"+message.getPayload());
        MessageBase messageBase = JSON.parseObject(message.getPayload(),
                MessageBase.class);
        comfyuiMessageService.handleMessage(messageBase);
    }
}
