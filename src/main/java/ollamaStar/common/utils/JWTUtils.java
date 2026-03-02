package ollamaStar.common.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import ollamaStar.pojo.User;

public class JWTUtils {
    static final String key = "itheima.com";
    public static String getToken(User user){
        return JWT.create()
                .setPayload("uid",user.getId())
                .setPayload("uname",user.getNickname())
                .setExpiresAt(DateTime.now().offsetNew(DateField.YEAR,1))
                .setKey(key.getBytes())
                .sign();
    }


//    /**
//     * 验证token是否有效
//     * @param token
//     * @return
//     */
//    public static boolean verifyToken(String token){
//        return JWT.of(token)
//                .setKey(key.getBytes())
//                .verify();
//    }
//

    public static User getToken(String token){
        try{
            JWT jwt = JWT.of(token).setKey(key.getBytes());
            User user = new User();
            user.setId(Long.valueOf(jwt.getPayload("uid")+""));
            user.setUsername(jwt.getPayload("uname")+"");
            return user;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

