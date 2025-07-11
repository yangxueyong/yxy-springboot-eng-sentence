package com.example.eng.config.aop;

import com.example.eng.config.annotation.CustomVerifyUser;
import com.example.eng.config.interceptor.UserContext;
import com.example.eng.constant.MyConstant;
import com.example.eng.entity.user.User;
import com.example.eng.util.VerifyUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
        String notTypeError = ann.notTypeError();
        String yesTypeError = ann.yesTypeError();
        String msg = ann.msg();

        boolean isGeneral = VerifyUserUtil.verifyMyIsGeneral();
        boolean isMember = VerifyUserUtil.verifyMyIsMember();
        boolean isVisitor = VerifyUserUtil.verifyMyIsVisitor();

        if(!StringUtils.isEmpty(notTypeError)){
            if(notTypeError.contains(MyConstant.USER_TYPE_MEMBER) && !isMember){
                throw new RuntimeException(msg);
            }
            if(notTypeError.contains(MyConstant.USER_TYPE_GENERAL) && !isGeneral){
                throw new RuntimeException(msg);
            }
        }
        if(!StringUtils.isEmpty(yesTypeError)){
            if(yesTypeError.contains(MyConstant.USER_TYPE_VISITOR) && isVisitor){
                throw new RuntimeException(msg);
            }
        }
    }
}
