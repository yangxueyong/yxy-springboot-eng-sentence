package com.example.eng.service.impl;

import com.example.eng.mapper.eng.EngSentenceMainMapper;
import com.example.eng.service.EngSentenceMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* EngSentenceMainImpl
*
* @author yangxueyong
* @date 2024-10-12 17:07:11
*/
@Service
public class EngSentenceMainServiceImpl implements EngSentenceMainService {
    @Autowired
    private EngSentenceMainMapper engSentenceMainMapper;

}
