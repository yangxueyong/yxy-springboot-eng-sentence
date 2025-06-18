package com.example.eng.config.aop;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;
import com.example.eng.config.annotation.CustomVerifyUser;
import com.example.eng.config.interceptor.UserContext;
import com.example.eng.constant.MyConstant;
import com.example.eng.entity.user.User;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 验证用户是否有权限操作
 * @author yxy
 * @date 2025/06/18
 */
@Slf4j
@Aspect
@Component
public class CustomVerifyUserAsp {

    @Before("@within(ann) || @annotation(ann)")
    public void before(JoinPoint joinPoint, CustomVerifyUser ann) {
        User user = UserContext.getUser();
        String userType = user.getUserType();
        Date memberDueDate = user.getMemberDueDate();
        if(ObjUtil.equal(MyConstant.USER_TYPE_GENERAL, userType)
                || (memberDueDate != null && memberDueDate.before(DateUtil.date()))){
            throw new RuntimeException("没有其他内容了---");
        }
    }
}
