package ollamaStar.server.controller;

import ollamaStar.pojo.dto.common.Result;
import ollamaStar.pojo.dto.request.UserLoginRequestDTO;
import ollamaStar.pojo.dto.respone.UserLoginResponeDTO;
import ollamaStar.server.service.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1.0/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<UserLoginResponeDTO> login(@RequestBody @Validated UserLoginRequestDTO userLoginRequestDTO) throws BadRequestException{
        return Result.ok(userService.loginByPassword(userLoginRequestDTO));
    }
}
