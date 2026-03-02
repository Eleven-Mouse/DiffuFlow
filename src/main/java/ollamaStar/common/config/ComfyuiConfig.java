package ollamaStar.common.config;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import ollamaStar.common.handler.ComfyuiMessageHandler;
import ollamaStar.server.api.ComfyuiApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Configuration
public class ComfyuiConfig
{
    @Bean
    public ComfyuiApi comfyuiApi() throws IOException {

        HttpLoggingInterceptor loggingInterceptor = new
                HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();


        //设置请求地址
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.129:8188")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        ComfyuiApi comfyuiApi = retrofit.create(ComfyuiApi.class);
        return comfyuiApi;
    }

//    连接管理器
    @Bean
    public ComfyuiMessageHandler comfyuiMessageHandler(){
        return new ComfyuiMessageHandler();
    }
    @Bean
    public WebSocketConnectionManager
    webSocketConnectionManager(ComfyuiMessageHandler comfyuiMessageHandler) {
        WebSocketClient client = new StandardWebSocketClient();
        String url = "ws://192.168.100.129:8188/ws?clientId=star-graph";
        WebSocketConnectionManager manager = new
                WebSocketConnectionManager(client,comfyuiMessageHandler,url);
        manager.start();
        return manager;
    }
}
