package com.example.eng.service;

import com.example.eng.entity.eng.EngSentenceMain;
import com.example.eng.entity.eng.io.EngSentenceMainIO;
import com.example.eng.entity.user.wechat.WechatMiniAppSessionKey;
import com.example.eng.entity.user.wechat.WechatUser;
import com.example.eng.entity.user.wechat.io.WechatMiniAppSessionIO;
import com.example.eng.entity.user.wechat.io.WechatTokenIO;

import java.util.List;

/**
* EngSentenceMainService
*
* @author yangxueyong
* @date 2024-10-12 17:07:11
*/
public interface WechatService {

    WechatMiniAppSessionKey getWechatMiniSessionKey(WechatTokenIO io);

    void saveWechatMiniSessionKey(WechatTokenIO io,WechatMiniAppSessionKey key);
}
