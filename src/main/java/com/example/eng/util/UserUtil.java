package com.example.eng.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.example.eng.config.interceptor.UserContext;
import com.example.eng.config.param.web.MyImgConfig;
import com.example.eng.config.param.wechat.WechatConfig;
import com.example.eng.constant.MyConstant;
import com.example.eng.entity.req.ReqBaseData;
import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.IOException;


/**
 * 用户相关
 *
 * @author yxy
 * @date 2025/07/07
 */
@Slf4j
public class UserUtil {

    /**
     * 获取获取微信token的url
     * @param wechatConfig
     * @return {@link String}
     */
    public static String getTokenUrl(WechatConfig wechatConfig) {
        ReqBaseData reqBaseData = UserContext.getObj(MyConstant.CACHE_KEY_REQ_BASE);
        String getTokenUrl = wechatConfig.getGetTokenUrl();
        if(ObjectUtil.equal(MyConstant.THIRDTYPE_WECHAT_GAME_NUM, reqBaseData.getThirdType())){
            getTokenUrl = wechatConfig.getGetGameNumTokenUrl();
        }
        return getTokenUrl;
    }

    /**
     * 获取应用id
     * @param wechatConfig
     * @return {@link String}
     */
    public static String getAppid(WechatConfig wechatConfig)  {
        ReqBaseData reqBaseData = UserContext.getObj(MyConstant.CACHE_KEY_REQ_BASE);
        String appid = wechatConfig.getAppid();
        if(ObjectUtil.equal(MyConstant.THIRDTYPE_WECHAT_GAME_NUM, reqBaseData.getThirdType())){
            appid = wechatConfig.getGameNumAppid();
        }
        return appid;
    }

    /**
     * 获取第三方方式
     * @return {@link String}
     */
    public static String getThirdType()  {
        ReqBaseData reqBaseData = UserContext.getObj(MyConstant.CACHE_KEY_REQ_BASE);
        return reqBaseData.getThirdType();
    }

}
