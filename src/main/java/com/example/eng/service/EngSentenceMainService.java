package com.example.eng.service;

import com.example.eng.entity.eng.EngSentenceMain;
import com.example.eng.entity.eng.io.EngSentenceCollectDelIO;
import com.example.eng.entity.eng.io.EngSentenceCollectIO;
import com.example.eng.entity.eng.io.EngSentenceMainIO;
import com.example.eng.entity.eng.io.EngSentenceMainNextIO;
import com.example.eng.entity.eng.vo.EngCollectVO;
import com.example.eng.entity.eng.vo.EngSentenceMainVO;

import java.util.List;

/**
* EngSentenceMainService
*
* @author yangxueyong
* @date 2024-10-12 17:07:11
*/
public interface EngSentenceMainService {
    List<EngSentenceMain> selectOrderBySort(EngSentenceMainIO record);

    EngSentenceMain selectOrderByRandom(EngSentenceMainIO io);

    EngSentenceMainVO getIndexEngMain(EngSentenceMainIO io);

    EngSentenceMainVO getIndexEngNextMain(EngSentenceMainNextIO ioo);

    EngSentenceMain selectNextBySort(EngSentenceMainIO io);

    EngSentenceMain selectUpBySort(EngSentenceMainIO io);

    void searchChangeEngMain(EngSentenceMainIO io);

    List<EngCollectVO> selectCollectByIO(EngSentenceCollectIO io);

    void delMyCollect(EngSentenceCollectDelIO io);
}
