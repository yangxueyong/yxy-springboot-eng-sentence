package com.example.eng.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;
import com.example.eng.config.interceptor.UserContext;
import com.example.eng.constant.MyConstant;
import com.example.eng.entity.user.User;

import java.util.Date;

/**
 * 验证用户信息
 * @author yxy
 * @date 2025/06/18
 */
public class VerifyUserUtil {

    public static boolean verifyUser() {
        User user = UserContext.getUser();
        String userType = user.getUserType();
        Date memberDueDate = user.getMemberDueDate();
        if(ObjUtil.equal(MyConstant.USER_TYPE_GENERAL, userType)
                || (memberDueDate != null && memberDueDate.before(DateUtil.date()))){
            throw new RuntimeException("没有其他内容了---");
        }
        return true;
    }
}
