package com.example.eng.service;

import com.example.eng.entity.eng.EngSentenceMain;
import com.example.eng.entity.eng.io.EngSentenceMainIO;

import java.util.List;

/**
* EngSentenceMainService
*
* @author yangxueyong
* @date 2024-10-12 17:07:11
*/
public interface EngSentenceMainService {
    List<EngSentenceMain> selectOrderBySort(EngSentenceMainIO record);
}
