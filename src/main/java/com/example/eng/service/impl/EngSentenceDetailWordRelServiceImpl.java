package com.example.eng.service.impl;

import com.example.eng.mapper.eng.EngSentenceDetailWordRelMapper;
import com.example.eng.service.EngSentenceDetailWordRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* EngSentenceDetailWordRelImpl
*
* @author yangxueyong
* @date 2024-10-16 19:39:59
*/
@Service
public class EngSentenceDetailWordRelServiceImpl implements EngSentenceDetailWordRelService {
    @Autowired
    private EngSentenceDetailWordRelMapper engSentenceDetailWordRelMapper;

}
