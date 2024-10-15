package com.example.eng.service.impl;

import com.example.eng.mapper.eng.EngSentenceUserPracticeMapper;
import com.example.eng.service.EngSentenceUserPracticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* EngSentenceUserPracticeImpl
*
* @author yangxueyong
* @date 2024-10-12 17:07:11
*/
@Service
public class EngSentenceUserPracticeServiceImpl implements EngSentenceUserPracticeService {
    @Autowired
    private EngSentenceUserPracticeMapper engSentenceUserPracticeMapper;

}
