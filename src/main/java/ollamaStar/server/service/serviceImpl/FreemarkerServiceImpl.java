package ollamaStar.server.service.serviceImpl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import ollamaStar.pojo.ComfyuiModel;
import ollamaStar.server.service.FreemarkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;


@Service
public class FreemarkerServiceImpl implements FreemarkerService {
    @Autowired
    Configuration configuration;

    @Override
    public String renderText2Image(ComfyuiModel comfyuiModel) throws Exception{
        Template template = configuration.getTemplate("t2i.fith");
        Map<String,Object> data = new HashMap<>();
        data.put("config",comfyuiModel);
        StringWriter out = new StringWriter();
        template.process(data,out);
        return out.toString();

    }


}
