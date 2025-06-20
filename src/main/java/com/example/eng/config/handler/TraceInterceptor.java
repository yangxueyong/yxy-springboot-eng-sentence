package com.example.eng.config.handler;


//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
import cn.hutool.core.lang.Snowflake;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author :XXX
 * @date :2020-12-08 12:00
 * @description :
 */
public class TraceInterceptor implements HandlerInterceptor {
    Snowflake snowflake = new Snowflake();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String traceIdVal = request.getHeader("traceId");
        if (!StringUtils.isEmpty(traceIdVal)) {
            MDC.put("feignId", traceIdVal);
            MDC.put("traceId", traceIdVal);
        }else{
            String id = snowflake.nextIdStr();
            MDC.put("feignId", id);
            MDC.put("traceId", id);
        }
        MDC.put("reqUrl",request.getServletPath());
        return true;
    }
}