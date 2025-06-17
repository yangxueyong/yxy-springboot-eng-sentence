package com.example.eng.service;

import com.example.eng.entity.user.User;
import com.example.eng.entity.user.wechat.io.WechatUserTokenIO;

/**
* UserService
*
* @author yangxueyong
* @date 2024-10-12 17:07:11
*/
public interface UserService {
    int insert(User record);

    int updateByPrimaryKeySelective(User record);

    int insertUser(User build);

    User selectByOpenId(WechatUserTokenIO io);

    int selectByOpenId(String openId);
}
