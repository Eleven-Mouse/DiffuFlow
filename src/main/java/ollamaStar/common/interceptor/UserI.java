package ollamaStar.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ollamaStar.common.utils.JWTUtils;
import ollamaStar.common.utils.UserUtils;
import ollamaStar.pojo.User;
import ollamaStar.pojo.dto.common.Result;
import ollamaStar.pojo.dto.common.ResultCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;


public class UserI implements HandlerInterceptor {
    /**
     * 处理controller之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 排除不需要解析用户的地址 ——比如登录地址直接放行
        String uri = request.getRequestURI();
        if(uri.contains("/api/1.0/user/login") || uri.contains("/error")){
            return true;
        }
        // 请求中拿到token     ———— header中token
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StrUtil.isEmpty(token)){
            writeAuthorizationFailed(response);
            return false;
        }

        // 存放到下上下文中      ———— UserUtils
        User user = JWTUtils.getToken(token);
        if(user == null){
            writeAuthorizationFailed(response);
            return false;
        }
        UserUtils.savaUser(user);
        return true;
    }

    /**
     * 封装未授权的返回结果信息
     * @param response
     */
    private void writeAuthorizationFailed(HttpServletResponse response) {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());


        Result<Object> failed = Result.failed(ResultCode.ACCESS_UNAUTHORIZED);
        String body = JSON.toJSONString(failed);

        try{
            PrintWriter writer = response.getWriter();
            writer.write(body);
            writer.flush();
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    /**
     * 处理controller之后
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {



    }

    /**
     * 做完所有mvc处理之后，返回前端数据之前，要做的事情
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
