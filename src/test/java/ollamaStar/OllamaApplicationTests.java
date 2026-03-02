package ollamaStar;

import cn.hutool.crypto.digest.BCrypt;
import ollamaStar.pojo.User;
import ollamaStar.server.ollama.client.api.OllamaApi;
import ollamaStar.server.ollama.client.pojo.OllamaChatRequest;
import ollamaStar.server.ollama.client.pojo.OllamaChatRespone;
import ollamaStar.server.ollama.client.pojo.OllamaMessage;
import ollamaStar.server.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;


@SpringBootTest
public class OllamaApplicationTests
{

  @Autowired
  OllamaApi ollamaApi;

  @Test
  public void test() throws IOException {
    OllamaMessage message = new OllamaMessage();
    message.setRole("user");
    message.setContent("帮我将以下中文翻译成英文：我的名字是tom，你叫什么？");
    OllamaChatRequest body = new OllamaChatRequest();
    body.setModel("qwen2.5:0.5b");
    body.setMessages(List.of(message));
    Call<OllamaChatRespone> chat = ollamaApi.chat(body);
    Response<OllamaChatRespone> result = chat.execute();
    OllamaChatRespone ollamaChatRespone = result.body();
    System.out.println(ollamaChatRespone.getMessage().getContent());

  }

}
