package com.example.eng.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.example.eng.config.interceptor.UserContext;
import com.example.eng.config.param.AudioParam;
import com.example.eng.config.param.translate.BaiduConfig;
import com.example.eng.config.param.translate.YoudaoConfig;
import com.example.eng.constant.MyConstant;
import com.example.eng.entity.eng.EngSentenceWord;
import com.example.eng.entity.eng.io.EngSentenceWordIO;
import com.example.eng.entity.eng.vo.EngCollectVO;
import com.example.eng.entity.eng.vo.EngSentenceWordCollectVO;
import com.example.eng.entity.eng.vo.EngSentenceWordVO;
import com.example.eng.entity.translate.YoudaoTranslate;
import com.example.eng.entity.translate.io.YoudaoTranslateEn2VoiceIO;
import com.example.eng.mapper.eng.EngSentenceWordMapper;
import com.example.eng.service.EngSentenceWordService;
import com.example.eng.service.EngUserOperService;
import com.example.eng.util.DownAudioUtil;
import com.example.eng.util.translate.baidu.BaiDuTransEntity;
import com.example.eng.util.translate.baidu.BaiDuTransUtil;
import com.example.eng.util.translate.youdao.TranslateYouDaoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
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

    @Autowired
    private EngUserOperService engUserOperService;
    @Autowired
    private AudioParam audioParam;

    @Autowired
    private BaiduConfig config;
    @Autowired
    private YoudaoConfig youdaoConfig;

    @Override
    public List<EngSentenceWordCollectVO> selectCollectWordByIO(EngSentenceWordIO io) {
        return engSentenceWordMapper.selectCollectWordByIO(io);
    }

    @Override
    public int updateByPrimaryKeySelective(EngSentenceWord record) {
        return engSentenceWordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<EngSentenceWord> selectAll() {
        return engSentenceWordMapper.selectAll();
    }

    @Override
    public List<EngCollectVO> selectCollectByIO() {
        EngSentenceWordIO io = new EngSentenceWordIO();
        io.setUserId(UserContext.getUser().getId());
        List<EngSentenceWordCollectVO> engSentenceWordCollectVOS = selectCollectWordByIO(io);
        List<EngCollectVO> vos = new ArrayList<>();
        for (EngSentenceWordCollectVO word : engSentenceWordCollectVOS) {
            setWordVoice(word);
            EngCollectVO vo = new EngCollectVO();
            vo.setId(String.valueOf(word.getCollectId()));
            vo.setEnName(word.getEnName());
            vo.setZnName(word.getZnName());
            vo.setWebAudioPath(word.getWebAudioPath());
            vo.setCreateTime(word.getCreateTime());
            vos.add(vo);
        }
        engUserOperService.getOper(vos, MyConstant.DATA_TYPE_WORD);
        return vos;
    }

    @Override
    public List<EngSentenceWordVO> selectByIO(EngSentenceWordIO io) {
        List<EngSentenceWord> engSentenceWords = engSentenceWordMapper.selectByIO(io);
        List<EngSentenceWordVO> vos = new ArrayList<>();
        for (EngSentenceWord word : engSentenceWords) {
            setWordVoice(word);
            EngSentenceWordVO vo = new EngSentenceWordVO();
            BeanUtils.copyProperties(word,vo);
            vos.add(vo);
        }

        engUserOperService.getOper(vos, MyConstant.DATA_TYPE_WORD);
        return vos;
    }



    /**
     * 设置单词的音频
     * @param word
     */
    private void setWordVoice(EngSentenceWord word) {
        String id = word.getId();
        String enName = word.getEnName();
        String znName = word.getZnName();
        if (StrUtil.isEmptyIfStr(znName) && !StrUtil.isEmptyIfStr(enName)) {
            try {
                BaiDuTransEntity translate = BaiDuTransUtil.translate(config, enName);
                BaiDuTransEntity.TransResult transResult = translate.getTrans_result().get(0);
                word.setZnName(transResult.getDst());

                EngSentenceWord word2 = new EngSentenceWord();
                word2.setId(id);
                word2.setZnName(transResult.getDst());
                word2.setUpdateTime(DateUtil.date());
                updateByPrimaryKeySelective(word2);
            }catch (Exception e){
                log.error("翻译报错->",e);
            }
        }

        String localAudioPath = word.getLocalAudioPath();
        //如果本地音频存在，则返回
        if(!StrUtil.isEmptyIfStr(localAudioPath)
//                && FileUtil.exists(Path.of(localAudioPath),true)
//                && new File(localAudioPath).length() > 500
        ){
            return;
        }

        localAudioPath = audioParam.getWordLocalPath() + id + ".mp3";
        try {
            String usaUrl = StrUtil.format(audioParam.getUsaUrl(), enName);
            DownAudioUtil.downAudioToLocal(usaUrl, localAudioPath);
            if(new File(localAudioPath).length() < 500){
                throw new RuntimeException("音频下载失败："+id);
            }
        }catch (Exception e){
            log.error("下载USA音频报错->{}",e.getMessage());
            YoudaoTranslate youdaoTranslate = TranslateYouDaoUtil.translateEn2Voice(youdaoConfig, YoudaoTranslateEn2VoiceIO.builder().query(enName).build());
            //下载音频
            DownAudioUtil.downAudioToLocal(youdaoTranslate.getSpeakUrl(), localAudioPath);
        }
        EngSentenceWord word2 = new EngSentenceWord();
        word2.setId(id);
        word2.setLocalAudioPath(localAudioPath);
        word2.setUpdateTime(DateUtil.date());
        updateByPrimaryKeySelective(word2);
    }



    @Override
    public void setAllWordVoice() {
        List<EngSentenceWord> engSentenceWords = selectAll();
        for (EngSentenceWord engSentenceWord : engSentenceWords) {
            setWordVoice(engSentenceWord);
        }
    }


}
