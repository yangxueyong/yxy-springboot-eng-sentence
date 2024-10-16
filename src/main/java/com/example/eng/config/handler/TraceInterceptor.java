package com.example.eng.config.handler;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * @author :XXX
 * @date :2020-12-08 12:00
 * @description :
 */
public class TraceInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String traceIdVal = request.getHeader("traceId");
        if (!StringUtils.isEmpty(traceIdVal)) {
            MDC.put("feignId", traceIdVal);
            MDC.put("traceId", traceIdVal);
        }
        MDC.put("reqUrl",request.getServletPath());
//        else {
//            MDC.remove(LogCollectionConstants.traceId);
//        }
        return true;
    }
}