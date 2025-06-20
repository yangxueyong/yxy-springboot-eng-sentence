package com.example.eng.service;

import com.example.eng.entity.eng.EngSentenceDetailWordRel;
import com.example.eng.entity.eng.io.EngSentenceDetailWordRelIO;

import java.util.List;

/**
* EngSentenceDetailWordRelService
*
* @author yangxueyong
* @date 2024-10-16 19:39:59
*/
public interface EngSentenceDetailWordRelService {
    List<EngSentenceDetailWordRel> selectByIO(EngSentenceDetailWordRelIO io);
}
