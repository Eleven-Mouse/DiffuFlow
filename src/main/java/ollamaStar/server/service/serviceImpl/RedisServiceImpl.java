package ollamaStar.server.service.serviceImpl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import ollamaStar.pojo.ComfyuiTask;
import ollamaStar.server.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisServiceImpl implements RedisService {
    private final static String TASK_KEY_PREFIX = "task_";
    private final static String DISTRIBUTED_ID_KEY = "DISTRIBUTED_ID";
    private final static String DISTRIBUTED_QUEUE_KEY = "DISTRIBUTED_QUEUE";

    private final static String RUN_TASK_KEY = "run_task_";

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public ComfyuiTask addQueueTask(ComfyuiTask comfyuiTask){

        //设置号码牌
        Long idex = stringRedisTemplate.opsForValue().increment(DISTRIBUTED_ID_KEY);

        //把号码贴在身上
        comfyuiTask.setIndex(idex);

        // 添加任务数据
        stringRedisTemplate.opsForValue().set(String.format("%s%s",TASK_KEY_PREFIX,comfyuiTask.getId()), JSON.toJSONString(comfyuiTask));

        //压入队列
        stringRedisTemplate.opsForZSet().add(DISTRIBUTED_QUEUE_KEY,comfyuiTask.getId(),idex);

        return comfyuiTask;
    }


    @Override
    public ComfyuiTask popQueueTask(){
        Long size = stringRedisTemplate.opsForZSet().size(DISTRIBUTED_QUEUE_KEY);
        if(size > 0){
            ZSetOperations.TypedTuple<String> task = stringRedisTemplate.opsForZSet().popMin(DISTRIBUTED_QUEUE_KEY);
            if(task != null){
                String value = task.getValue();
                String json = stringRedisTemplate.opsForValue().getAndDelete(String.format("%s%s",TASK_KEY_PREFIX,value));
                if(StrUtil.isNotEmpty(json)){
                    ComfyuiTask comfyuiTask = JSON.parseObject(json,ComfyuiTask.class);
                    return comfyuiTask;
                }
            }
        }
        return null;
    }

    @Override
    public void addStartedTask(String promptId, ComfyuiTask task) {
        stringRedisTemplate.opsForValue().set(String.format("%s%s", RUN_TASK_KEY, promptId), JSON.toJSONString(task), Duration.ofMinutes(10));
    }


    @Override
    public ComfyuiTask getStartedTask(String promptId) {
        String json = stringRedisTemplate.opsForValue().get(String.format("%s%s", RUN_TASK_KEY, promptId));
        if(json!=null){
            return JSON.parseObject(json,ComfyuiTask.class);
        }
        return null;
    }
}
