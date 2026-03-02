package ollamaStar.server.service.serviceImpl;

import ollamaStar.server.ollama.client.api.OllamaApi;
import ollamaStar.server.ollama.client.pojo.OllamaChatRequest;
import ollamaStar.server.ollama.client.pojo.OllamaChatRespone;
import ollamaStar.server.ollama.client.pojo.OllamaMessage;
import ollamaStar.server.service.OllamaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.util.List;
@Service
public class OllamaServiceImpl implements OllamaService {
    @Autowired
    OllamaApi ollamaApi;

    @Override
    public String translate(String propmt) {
        try{
            OllamaMessage message = new OllamaMessage();
            message.setRole("user");
            message.setContent("帮我将以下文本内容翻译成英文：" + propmt);
            OllamaChatRequest body = new OllamaChatRequest();
            body.setModel("qwen2.5:0.5b");
            body.setMessages(List.of(message));
            Call<OllamaChatRespone> chat = ollamaApi.chat(body);
            Response<OllamaChatRespone> result = chat.execute();
            OllamaChatRespone ollamaChatRespone = result.body();
            return ollamaChatRespone.getMessage().getContent();

    }catch (Exception e){
            e.printStackTrace();
        }
        return propmt;
    }
}
