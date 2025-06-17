package com.example.eng.service;

import com.example.eng.entity.eng.EngSentenceWord;
import com.example.eng.entity.eng.io.EngSentenceWordIO;
import com.example.eng.entity.eng.vo.EngSentenceWordVO;

import java.util.List;

/**
* EngSentenceWordService
*
* @author yangxueyong
* @date 2024-10-16 19:39:59
*/
public interface EngSentenceWordService {
    List<EngSentenceWordVO> selectByIO(EngSentenceWordIO io);

    int updateByPrimaryKeySelective(EngSentenceWord record);

    List<EngSentenceWord> selectAll();

    void setAllWordVoice();
}
