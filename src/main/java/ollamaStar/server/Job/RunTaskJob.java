package ollamaStar.server.Job;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import ollamaStar.pojo.ComfyuiTask;
import ollamaStar.server.api.ComfyuiApi;
import ollamaStar.server.service.RedisService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;

import java.util.HashMap;

@Component
@Slf4j
public class RunTaskJob
{
    private static final String SPRING_TASK_LOCK_KEY = "SPRING_TASK_LOCK_KEY";
    @Autowired
    RedisService redisService;
    @Autowired
    ComfyuiApi comfyuiApi;
    @Autowired
    RedissonClient redissonClient;

    private void addTaskQueue(){
        try{
            ComfyuiTask task = redisService.popQueueTask();
            if(task == null){
                return;
            }

            Call<HashMap> addQueueTask = comfyuiApi.addQueueTask(task.getComfyuiRequestDto());

            Response<HashMap> response = addQueueTask.execute();
            if(response.isSuccessful()){
                HashMap data = response.body();
                task.setPromptId(data.get("prompt_id").toString());
                log.info("添加任务到Comfyui成功：{}",task.getPromptId());
            }else{
                String error = response.errorBody().string();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Scheduled(cron = "*/1 * * * * ?")
    public void task(){
        RLock lock = redissonClient.getLock(SPRING_TASK_LOCK_KEY);
        if (lock. tryLock()) {
            try {
                addTaskQueue();
            }finally {
                lock.unlock();
            }
        }
    }

}
