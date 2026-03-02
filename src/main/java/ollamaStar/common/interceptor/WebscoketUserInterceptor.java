package ollamaStar.common.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.security.Principal;
import java.util.List;

public class WebscoketUserInterceptor implements ChannelInterceptor
{
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if
        (accessor!=null&& StompCommand.CONNECT.equals(accessor.getCommand())) {
            List<String> clientIds = accessor.getNativeHeader("clientId");
            if(clientIds!=null&&clientIds.size()>0) {
                accessor.setUser(new Principal() {
                    @Override
                    public String getName() {
                        return clientIds.get(0);
                    }
                });
            }
        }
        return message;
    }
}


