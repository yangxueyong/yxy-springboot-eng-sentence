package com.example.eng.service.impl;

import com.example.eng.mapper.eng.EngSentenceWordDetailMapper;
import com.example.eng.service.EngSentenceWordDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* EngSentenceWordDetailImpl
*
* @author yangxueyong
* @date 2024-10-12 17:07:11
*/
@Service
public class EngSentenceWordDetailServiceImpl implements EngSentenceWordDetailService {
    @Autowired
    private EngSentenceWordDetailMapper engSentenceWordDetailMapper;

}
