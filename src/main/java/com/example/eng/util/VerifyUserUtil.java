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

    /**
     * 判断是否为会员用户
     * @return boolean
     */
    public static boolean verifyUserIsMember() {
        if(verifyUserIsGeneral()){
            throw new RuntimeException("没有其他内容了---");
        }
        return true;
    }

    /**
     * 判断用户是否为普通用户
     * @return boolean
     */
    public static boolean verifyUserIsGeneral() {
        User user = UserContext.getUser();
        String userType = user.getUserType();
        Date memberDueDate = user.getMemberDueDate();
        if(ObjUtil.equal(MyConstant.USER_TYPE_GENERAL, userType)
                || memberDueDate == null
                || memberDueDate.before(DateUtil.date())){
            return true;
        }
        return false;
    }
}
