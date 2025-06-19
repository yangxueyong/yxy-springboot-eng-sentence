package com.example.eng.config.interceptor;
 
import cn.hutool.core.util.StrUtil;
import com.example.eng.config.param.wechat.WechatConfig;
import com.example.eng.constant.MyConstant;
import com.example.eng.entity.user.User;
import com.example.eng.entity.user.wechat.io.WechatUserTokenIO;
import com.example.eng.service.UserService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {
 
    private static final String ERROR_CODE_401 = "401";
 
    @Autowired
    private UserService userService;

    @Autowired
    private WechatConfig wechatConfig;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String openId = request.getHeader("openId"); //header里面传过来的参数
        String appId = request.getHeader("appId");
        String thirdType = request.getHeader("thirdType");
        if (StrUtil.isBlank(openId)) {
            //url参数 ？token=xxx
            openId = request.getParameter("openId");
        }
        if(StrUtil.isBlank(thirdType)){
            thirdType = request.getParameter("thirdType");
        }
        //这两种传过来的参数有任意一种里面有token，我们就认为有！
 
        // 执行认证
        if (StrUtil.isBlank(openId)) {
            throw new RuntimeException("无token，请重新登录");
        }
        if(MyConstant.THIRDTYPE_WECHAT.equals(thirdType)){
            appId = wechatConfig.getAppid();
        }
        WechatUserTokenIO io = WechatUserTokenIO.builder()
                .appId(appId)
                .thirdType(thirdType)
                .openId(openId)
                .build();
        log.info("正在验证用户-> openId:{},appId:{},thirdType:{}",openId,appId,thirdType);
        User user = userService.selectByOpenId(io);
        if(user == null){
            throw new RuntimeException("登录信息无效，请重新登录");
        }
        if(MyConstant.STATUS_AVAILABLE.equalsIgnoreCase(user.getStatus())){
            throw new RuntimeException("用户异常，请联系管理员");
        }
        UserContext.setUser(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        UserContext.clearUser();
    }
}