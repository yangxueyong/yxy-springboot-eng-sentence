package com.example.eng.service.impl;

import com.example.eng.entity.user.User;
import com.example.eng.entity.user.wechat.io.WechatUserTokenIO;
import com.example.eng.mapper.eng.UserMapper;
import com.example.eng.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UserImpl
*
* @author yangxueyong
* @date 2024-10-12 17:07:11
*/
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByUnqSelective(User record) {
        return userMapper.updateByUnqSelective(record);
    }

    public int insertUser(User record) {
        int i = 0;
        try {
            int i1 = updateByUnqSelective(record);
            if(i1 <= 0) {
                i = insert(record);
            }
        }catch (Exception e){
            log.error("insert user error->{}", e.getMessage());
        }
        return i;
    }

    @Override
    public User selectByOpenId(WechatUserTokenIO io) {
        return userMapper.selectByOpenId(io);
    }

    @Override
    public int selectByOpenId(String openId) {
        return 0;
    }


}
