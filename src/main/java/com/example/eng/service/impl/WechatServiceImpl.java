package com.example.eng.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.example.eng.config.param.wechat.WechatConfig;
import com.example.eng.constant.MyConstant;
import com.example.eng.entity.user.User;
import com.example.eng.entity.user.wechat.WechatMiniAppSessionKey;
import com.example.eng.entity.user.wechat.io.WechatTokenIO;
import com.example.eng.service.UserService;
import com.example.eng.service.WechatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import java.net.http.HttpClient;
import java.util.Map;

/**
* 微信相关信息
*
* @author yangxueyong
* @date 2024-10-12 17:07:11
*/
@Slf4j
@Service
public class WechatServiceImpl implements WechatService {

    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private UserService userService;

    public WechatMiniAppSessionKey getWechatMiniSessionKey(WechatTokenIO io) {
        //先获取token 再获取用户信息
        Map<Object, Object> codeMap = MapUtil.builder()
                .put("code", io.getCode())
                .build();
        String getTokenUrl = StrUtil.format(wechatConfig.getGetTokenUrl(), codeMap);

        HttpRequest httpRequest = HttpRequest.get(getTokenUrl);
        HttpResponse execute = httpRequest.execute();
        if(!execute.isOk()){
            throw new RuntimeException("获取token失败");
        }
        String accesss = execute.body();
        log.info("获取token:{}", accesss);
        WechatMiniAppSessionKey wechatMiniAppSessionKey = JSON.parseObject(accesss, WechatMiniAppSessionKey.class);
        saveWechatMiniSessionKey(io, wechatMiniAppSessionKey);
        return wechatMiniAppSessionKey;
    }

    /**
     * 保存用户信息到数据库
     * @param io
     * @param key
     */
    @Override
    public void saveWechatMiniSessionKey(WechatTokenIO io,WechatMiniAppSessionKey key) {
        userService.insertUser(User.builder()
                        .appId(wechatConfig.getAppid())
                        .thirdType(MyConstant.THIRDTYPE_WECHAT)
                        .openId(key.getOpenid())
                        .sessionKey(key.getSession_key())
                        .enName(io.getWechatUser().getNickName())
                        .thirdHeadImgPath(io.getWechatUser().getAvatarUrl())
                        .createTime(DateUtil.date())
                .build());
    }
}
