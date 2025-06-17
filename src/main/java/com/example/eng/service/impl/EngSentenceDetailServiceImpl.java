package com.example.eng.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.example.eng.config.param.AudioParam;
import com.example.eng.config.translate.YoudaoConfig;
import com.example.eng.entity.eng.EngSentenceDetail;
import com.example.eng.entity.eng.EngSentenceMain;
import com.example.eng.entity.eng.io.EngSentenceDetailIO;
import com.example.eng.entity.eng.vo.EngSentenceDetailVO;
import com.example.eng.entity.translate.YoudaoTranslate;
import com.example.eng.entity.translate.io.YoudaoTranslateEn2VoiceIO;
import com.example.eng.mapper.eng.EngSentenceDetailMapper;
import com.example.eng.service.EngSentenceDetailService;
import com.example.eng.util.DownAudioUtil;
import com.example.eng.util.translate.youdao.TranslateYouDaoUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Call;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
* EngSentenceDetailImpl
*
* @author yangxueyong
* @date 2024-10-12 17:07:11
*/
@Slf4j
@Service
public class EngSentenceDetailServiceImpl implements EngSentenceDetailService {

    @Autowired
    private AudioParam audioParam;

    @Autowired
    private EngSentenceDetailMapper engSentenceDetailMapper;

    @Autowired
    private YoudaoConfig youdaoConfig;

    @Override
    public List<EngSentenceDetail> selectListByIO(EngSentenceDetailIO io) {
        return engSentenceDetailMapper.selectByIO(io);
    }

    @Override
    public List<EngSentenceDetail> selectAllDetailByIO(EngSentenceDetailIO io) {
        List<EngSentenceDetail> dataList = selectListByIO(io);
        if(CollectionUtil.isEmpty(dataList)){
            return null;
        }
        for (EngSentenceDetail engSentenceDetail : dataList) {
            getAndDownDetail(engSentenceDetail);
        }
        return dataList;
    }

    @Override
    public List<EngSentenceDetailVO> getEngSentenceDetailVOS(EngSentenceMain main) {
        //获取该主表的句子信息
        EngSentenceDetailIO io = new EngSentenceDetailIO();
        io.setMainId(main.getId());
        List<EngSentenceDetail> engSentenceDetails = selectAllDetailByIO(io);

        List<EngSentenceDetailVO> detailVOS = new ArrayList<>();
        for (EngSentenceDetail engSentenceDetail : engSentenceDetails) {
            EngSentenceDetailVO voo = new EngSentenceDetailVO();
            BeanUtil.copyProperties(engSentenceDetail,voo);
            detailVOS.add(voo);
        }
        return detailVOS;
    }

    /**
     * @param record
     * @return int
     */
    @Override
    public int updateByPrimaryKeySelective(EngSentenceDetail record) {
        return engSentenceDetailMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<EngSentenceDetail> selectAll() {
        return engSentenceDetailMapper.selectAll();
    }

    @Override
    public void downloadAllDetail() {
        List<EngSentenceDetail> engSentenceDetailList = selectAll();
        for (EngSentenceDetail engSentenceDetail : engSentenceDetailList) {
            getAndDownDetail(engSentenceDetail);
        }
    }

    /**
     * 获取详情
     * @param io
     * @return {@link EngSentenceDetail}
     */
    @Override
    public EngSentenceDetail selectDetailByIO(EngSentenceDetailIO io) {
        List<EngSentenceDetail> dataList = selectListByIO(io);
        if(CollectionUtil.isEmpty(dataList)){
            return null;
        }
        EngSentenceDetail engSentenceDetail = dataList.get(0);
        return getAndDownDetail(engSentenceDetail);
    }

    @NotNull
    private EngSentenceDetail getAndDownDetail(EngSentenceDetail engSentenceDetail) {
        String localAudioPath = engSentenceDetail.getLocalAudioPath();

        //如果本地音频存在，则返回
        if(!StrUtil.isEmptyIfStr(localAudioPath)
                && FileUtil.exists(Path.of(localAudioPath),true)
                && new File(localAudioPath).length() > 500){
            return engSentenceDetail;
        }
        //如果本地音频不存在，则下载音频
        String enName = engSentenceDetail.getEnName();
        String id = engSentenceDetail.getId();
//        String usaUrl = StrUtil.format(audioParam.getUsaUrl(), enName);

        localAudioPath = audioParam.getLocalPath() + id + ".mp3";

        YoudaoTranslate youdaoTranslate = TranslateYouDaoUtil.translateEn2Voice(youdaoConfig, YoudaoTranslateEn2VoiceIO.builder().query(enName).build());
        //下载音频
        DownAudioUtil.downAudioToLocal(youdaoTranslate.getSpeakUrl(), localAudioPath);
        engSentenceDetail.setLocalAudioPath(localAudioPath);

        EngSentenceDetail updateEngSentenceDetail = new EngSentenceDetail();
        updateEngSentenceDetail.setId(id);
        updateEngSentenceDetail.setLocalAudioPath(localAudioPath);
        updateEngSentenceDetail.setUpdateTime(DateUtil.date());
        //更新数据库
        updateByPrimaryKeySelective(updateEngSentenceDetail);
        return engSentenceDetail;
    }


}
