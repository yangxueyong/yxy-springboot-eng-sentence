package com.example.eng.service;

import com.example.eng.entity.eng.EngSentenceDetail;
import com.example.eng.entity.eng.EngSentenceMain;
import com.example.eng.entity.eng.io.EngSentenceDetailIO;
import com.example.eng.entity.eng.vo.EngCollectVO;
import com.example.eng.entity.eng.vo.EngSentenceDetailCollectVO;
import com.example.eng.entity.eng.vo.EngSentenceDetailVO;

import java.util.List;

/**
* EngSentenceDetailService
*
* @author yangxueyong
* @date 2024-10-12 17:07:11
*/
public interface EngSentenceDetailService {
    EngSentenceDetail selectByPrimaryKey(String id);

    List<EngSentenceDetail> selectListByIO(EngSentenceDetailIO io);

    List<EngSentenceDetail> selectAllDetailByIO(EngSentenceDetailIO io);
    List<EngSentenceDetailVO> getEngSentenceDetailVOS(EngSentenceMain main);

    EngSentenceDetail selectDetailByIO(EngSentenceDetailIO io);

    int updateByPrimaryKeySelective(EngSentenceDetail record);

    List<EngSentenceDetail> selectAll();

    void downloadAllDetail();

    List<EngSentenceDetailCollectVO> selectCollectDetailByIO(EngSentenceDetailIO io);

    List<EngCollectVO> selectCollectByIO();
}
