package ollamaStar;

import ollamaStar.pojo.ComfyuiModel;
import ollamaStar.server.ollama.client.api.OllamaApi;
import ollamaStar.server.ollama.client.pojo.OllamaChatRequest;
import ollamaStar.server.ollama.client.pojo.OllamaChatRespone;
import ollamaStar.server.ollama.client.pojo.OllamaMessage;
import ollamaStar.server.service.FreemarkerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;


@SpringBootTest
public class FreeMarkerTests
{

  @Autowired
  FreemarkerService freemarkerService;

  @Test
  public void test() throws Exception {
    ComfyuiModel model = new ComfyuiModel();
    model.setPropmt("a photo of an astronaut riding a horse on mars");
    model.setReverse("bad legs");
    model.setSamplerName("Euler");
    model.setScheduler("normal");
    model.setSize(1);
    model.setCfg(7);
    model.setWidth(512);
    model.setHeight(512);
    model.setStep(20);
    model.setSeed(0);
    model.setModelName("majicmixRealistic_v7.safetensors");
    String json = freemarkerService.renderText2Image(model);
    System.out.println(json);


  }

}
