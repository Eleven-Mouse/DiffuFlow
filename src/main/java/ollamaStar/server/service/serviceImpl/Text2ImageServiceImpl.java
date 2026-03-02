package ollamaStar.server.service.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import ollamaStar.pojo.ComfyuiModel;
import ollamaStar.pojo.ComfyuiRequestDto;
import ollamaStar.pojo.ComfyuiTask;
import ollamaStar.pojo.dto.respone.Text2ImageReqDto;
import ollamaStar.pojo.dto.respone.Text2ImageResDto;
import ollamaStar.server.api.ComfyuiApi;
import ollamaStar.server.service.FreemarkerService;
import ollamaStar.server.service.OllamaService;
import ollamaStar.server.service.RedisService;
import ollamaStar.server.service.Text2ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Text2ImageServiceImpl implements Text2ImageService {
    public static final String COMFYUI_CLIENT_ID = "star-graph";
    @Autowired
    ComfyuiApi comfyuiApi;
    @Autowired
    OllamaService ollamaService;
    @Autowired
    FreemarkerService freemarkerService;
    @Autowired
    RedisService redisService;

    /**
     * 获取Comfyui任务
     * @param text2ImageResDto
     * @return
     * @throws Exception
     */
    public ComfyuiTask getComfyuiTask(Text2ImageResDto text2ImageResDto)
            throws Exception {
        // 数据转换
        ComfyuiModel model = BeanUtil.toBean(text2ImageResDto,ComfyuiModel.class);

        model.setWidth(text2ImageResDto.width());
        model.setHeight(text2ImageResDto.height());
        model.setSamplerName(text2ImageResDto.samplerName());
        model.setScheduler(text2ImageResDto.scheduler());
        model.setModelName(text2ImageResDto.modelName());

        // 翻译
        model.setPropmt(String.format("(8k, best quality, masterpiece), (high detailed skin),%s", ollamaService.translate(model.getPropmt())));
        if(StrUtil.isNotEmpty(model.getReverse())){
            model.setReverse(ollamaService.translate(model.getReverse()));
        }
        model.setReverse(String.format("%s,bad face,naked,bad finger,bad arm,bad leg,bad eye", model.getReverse()));
        // 模版生成json字符串
        String prompt = freemarkerService.renderText2Image(model);
        ComfyuiRequestDto dto = new ComfyuiRequestDto(COMFYUI_CLIENT_ID,
            JSON.parseObject(prompt));
        // 封装task任务并返回
        ComfyuiTask task = new ComfyuiTask(text2ImageResDto.getClientId(), dto);
        return task;
    }

    /**
     * 实现文生图方法
     * @param text2ImageResDto
     * @return
     */
    public Text2ImageReqDto propmt(Text2ImageResDto text2ImageResDto){
        try{
            //详细的“派工单”
            ComfyuiTask comfyuiTask = getComfyuiTask(text2ImageResDto);

            //把封装好的任务加入redis中
            comfyuiTask = redisService.addQueueTask(comfyuiTask);
            Text2ImageReqDto text2ImageReqDto = new Text2ImageReqDto();
            text2ImageReqDto.setPid(comfyuiTask.getId());
            text2ImageReqDto.setQueueIndex(comfyuiTask.getIndex());
            return text2ImageReqDto;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }






}

