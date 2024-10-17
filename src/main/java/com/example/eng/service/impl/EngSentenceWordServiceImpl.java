package com.example.eng.service.impl;

import cn.hutool.core.util.StrUtil;
import com.example.eng.entity.eng.EngSentenceWord;
import com.example.eng.entity.eng.io.EngSentenceWordIO;
import com.example.eng.mapper.eng.EngSentenceWordMapper;
import com.example.eng.service.EngSentenceWordService;
import com.example.eng.service.translate.BaiDuTransEntity;
import com.example.eng.service.translate.BaiDuTransUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* EngSentenceWordImpl
*
* @author yangxueyong
* @date 2024-10-16 19:39:59
*/
@Slf4j
@Service
public class EngSentenceWordServiceImpl implements EngSentenceWordService {
    @Autowired
    private EngSentenceWordMapper engSentenceWordMapper;

    @Override
    public List<EngSentenceWord> selectByIO(EngSentenceWordIO io) {
        List<EngSentenceWord> engSentenceWords = engSentenceWordMapper.selectByIO(io);
        for (EngSentenceWord word : engSentenceWords) {
            String enName = word.getEnName();
            String znName = word.getZnName();
            if (StrUtil.isEmptyIfStr(znName) && !StrUtil.isEmptyIfStr(enName)) {
                try {
                    BaiDuTransEntity translate = BaiDuTransUtil.translate(enName, "en", "zh");
                    BaiDuTransEntity.TransResult transResult = translate.getTrans_result().get(0);
                    word.setZnName(transResult.getDst());

                    EngSentenceWord word2 = new EngSentenceWord();
                    word2.setId(word.getId());
                    word2.setZnName(transResult.getDst());
                    updateByPrimaryKeySelective(word2);
                }catch (Exception e){
                    log.error("翻译报错->",e);
                }
            }
        }
        return engSentenceWords;
    }

    @Override
    public int updateByPrimaryKeySelective(EngSentenceWord record) {
        return engSentenceWordMapper.updateByPrimaryKeySelective(record);
    }
}
