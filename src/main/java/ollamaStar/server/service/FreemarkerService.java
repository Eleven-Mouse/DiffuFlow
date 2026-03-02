package ollamaStar.server.service;

import ollamaStar.pojo.ComfyuiModel;
import org.springframework.stereotype.Service;

@Service
public interface FreemarkerService {
    String renderText2Image(ComfyuiModel comfyuiModel) throws Exception;
}
