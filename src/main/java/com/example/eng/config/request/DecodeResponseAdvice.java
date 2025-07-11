package com.example.eng.config.request;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.eng.config.interceptor.UserContext;
import com.example.eng.constant.MyConstant;
import com.example.eng.entity.res.R;
import com.example.eng.entity.user.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * 请求参数手动验证
 *
 * @author yangxueyong
 * @date 2021/06/02
 */
@Slf4j
@ControllerAdvice
public class DecodeResponseAdvice implements ResponseBodyAdvice {


    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        try {
            if(body instanceof R){
                R r = (R) body;
                User loginUser = UserContext.getUser();
                if(loginUser != null){
                    if(StrUtil.isEmptyIfStr(loginUser.getUserType())){
                        loginUser.setUserType(MyConstant.USER_TYPE_VISITOR);
                    }
                    r.setMyUser(loginUser);
                }
            }
        }catch (Exception e){
            log.warn("无法设置user->{}", e.getMessage());
        }

        try {
            log.info("返参->{}", JSON.toJSONString(body));
        }catch (Exception e){
            log.warn("参数无法序列化->{}", e.getMessage());
        }
        return body;
    }
}