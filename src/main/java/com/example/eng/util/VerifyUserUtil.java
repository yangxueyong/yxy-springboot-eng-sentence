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
        //如果不是会员
        if(!verifyMyIsMember()){
            throw new RuntimeException("没有其他内容了---");
        }
        return true;
    }

    /**
     * 如果是游客则报错
     * @return boolean
     */
    public static boolean verifyUserIsVisitor(String msg) {
        //如果是普通用户就报错
        if(verifyMyIsVisitor()){
            throw new RuntimeException("请先登录哦---" + msg);
        }
        return true;
    }

    /**
     * 判断用户是否为游客
     * @return boolean
     */
    public static boolean verifyMyIsVisitor() {
        User user = UserContext.getUser();
        String userType = user.getUserType();
        Date memberDueDate = user.getMemberDueDate();
        if(ObjUtil.equal(MyConstant.USER_TYPE_VISITOR, userType)){
            return true;
        }
        return false;
    }


    /**
     * 判断用户是否为普通用户
     * @return boolean
     */
    public static boolean verifyMyIsGeneral() {
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

    /**
     * 判断用户是否为会员
     * @return boolean
     */
    public static boolean verifyMyIsMember() {
        User user = UserContext.getUser();
        String userType = user.getUserType();
        Date memberDueDate = user.getMemberDueDate();
        if(ObjUtil.equal(MyConstant.USER_TYPE_MEMBER, userType)
                && (memberDueDate != null && DateUtil.date().getTime() <= memberDueDate.getTime())){
            return true;
        }
        return false;
    }
}
