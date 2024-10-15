package com.example.eng.service.impl;

import com.example.eng.mapper.eng.UserMapper;
import com.example.eng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UserImpl
*
* @author yangxueyong
* @date 2024-10-12 17:07:11
*/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

}
