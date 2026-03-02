package ollamaStar.server.service.serviceImpl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ollamaStar.common.exception.CustonException;
import ollamaStar.common.utils.JWTUtils;
import ollamaStar.pojo.User;
import ollamaStar.pojo.dto.request.UserLoginRequestDTO;
import ollamaStar.pojo.dto.respone.UserLoginResponeDTO;
import ollamaStar.server.mapper.UserMapper;
import ollamaStar.server.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public UserLoginResponeDTO loginByPassword(UserLoginRequestDTO userLoginRequestDTO){

/*
  String str1 = "";
  String str2 = "  ";
  String str3 = null;
  String str4 = ";
  isEmpty 只会检测 1,3;
  isBlank 会检测 全部
 */
        //    参数判断
        if(StrUtil.isBlank(userLoginRequestDTO.getUsername())||StrUtil.isBlank(userLoginRequestDTO.getPassword())){
            throw new CustonException("用户名或密码不能为空！");
        }
        //    通过用户名查数据库，查 用户名和手机号
        LambdaQueryWrapper<User>  wapper =  Wrappers.<User>lambdaQuery().eq(User::getMobile,userLoginRequestDTO.getUsername())
                .or()
                .eq(User::getUsername,userLoginRequestDTO.getUsername());


        User user = baseMapper.selectOne(wapper);

        if(user == null){
            throw new CustonException("用户名或密码不正确！");
        }


        //    判断密码是否一致，数据加密（MD5+盐）
        //    将密码加密生成
        //    对比密码
        if(!BCrypt.checkpw(userLoginRequestDTO.getPassword(),user.getPassword())){
            throw new CustonException("用户名或密码不正确！");
        }

        //    生成token
        UserLoginResponeDTO userLoginResponeDTO = new UserLoginResponeDTO();
        userLoginResponeDTO.setAvatar(user.getAvatar());
        userLoginResponeDTO.setId(user.getId());
        userLoginResponeDTO.setName(user.getNickname());
        userLoginResponeDTO.setToken(JWTUtils.getToken(user));



    return userLoginResponeDTO;
    };
}
