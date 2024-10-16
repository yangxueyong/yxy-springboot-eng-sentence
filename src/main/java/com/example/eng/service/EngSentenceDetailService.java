package com.example.eng.service;

import com.example.eng.entity.eng.EngSentenceDetail;
import com.example.eng.entity.eng.io.EngSentenceDetailIO;

import java.util.List;

/**
* EngSentenceDetailService
*
* @author yangxueyong
* @date 2024-10-12 17:07:11
*/
public interface EngSentenceDetailService {
    List<EngSentenceDetail> selectListByIO(EngSentenceDetailIO io);

    EngSentenceDetail selectDetailByIO(EngSentenceDetailIO io);

    int updateByPrimaryKeySelective(EngSentenceDetail record);
}
