package com.example.eng.service.impl;

import com.example.eng.mapper.eng.EngSentenceDetailMapper;
import com.example.eng.service.EngSentenceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* EngSentenceDetailImpl
*
* @author yangxueyong
* @date 2024-10-12 17:07:11
*/
@Service
public class EngSentenceDetailServiceImpl implements EngSentenceDetailService {
    @Autowired
    private EngSentenceDetailMapper engSentenceDetailMapper;

}
