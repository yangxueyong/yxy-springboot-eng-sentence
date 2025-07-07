package com.example.eng.config.handler;


//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
import cn.hutool.core.lang.Snowflake;
import com.example.eng.config.interceptor.UserContext;
import com.example.eng.constant.MyConstant;
import com.example.eng.entity.req.ReqBaseData;
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
        String servletPath = request.getServletPath();
        String thirdType = request.getHeader("thirdType");

        MDC.put("reqUrl", servletPath);
        MDC.put("thirdType", thirdType);

        ReqBaseData req = ReqBaseData.builder()
                .reqUrl(servletPath)
                .thirdType(thirdType)
                .build();
        UserContext.setObj(MyConstant.CACHE_KEY_REQ_BASE, req);
        return true;
    }
}