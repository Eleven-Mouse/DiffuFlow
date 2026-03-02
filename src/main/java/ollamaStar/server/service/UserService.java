package ollamaStar.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ollamaStar.pojo.dto.request.UserLoginRequestDTO;
import ollamaStar.pojo.dto.respone.UserLoginResponeDTO;
import ollamaStar.pojo.User;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends IService<User> {
 UserLoginResponeDTO loginByPassword(UserLoginRequestDTO userLoginRequestDTO) throws
            BadRequestException;
}
