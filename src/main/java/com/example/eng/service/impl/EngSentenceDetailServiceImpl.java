package com.example.eng.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.example.eng.config.param.AudioParam;
import com.example.eng.entity.eng.EngSentenceDetail;
import com.example.eng.entity.eng.io.EngSentenceDetailIO;
import com.example.eng.mapper.eng.EngSentenceDetailMapper;
import com.example.eng.service.EngSentenceDetailService;
import com.example.eng.util.DownAudioUtil;
import org.aspectj.weaver.ast.Call;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.List;

/**
* EngSentenceDetailImpl
*
* @author yangxueyong
* @date 2024-10-12 17:07:11
*/
@Service
public class EngSentenceDetailServiceImpl implements EngSentenceDetailService {

    @Autowired
    private AudioParam audioParam;

    @Autowired
    private EngSentenceDetailMapper engSentenceDetailMapper;

    @Override
    public List<EngSentenceDetail> selectListByIO(EngSentenceDetailIO io) {
        return engSentenceDetailMapper.selectByIO(io);
    }

    /**
     * @param record
     * @return int
     */
    @Override
    public int updateByPrimaryKeySelective(EngSentenceDetail record) {
        return engSentenceDetailMapper.updateByPrimaryKeySelective(record);
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
        String localAudioPath = engSentenceDetail.getLocalAudioPath();

        //如果本地音频存在，则返回
        if(!StrUtil.isEmptyIfStr(localAudioPath) && FileUtil.exists(Path.of(localAudioPath),true)){
            return engSentenceDetail;
        }
        //如果本地音频不存在，则下载音频
        String enName = engSentenceDetail.getEnName();
        String id = engSentenceDetail.getId();
        String usaUrl = StrUtil.format(audioParam.getUsaUrl(), enName);
        localAudioPath = audioParam.getLocalPath() + id + ".mp3";
        //下载音频
        DownAudioUtil.downAudioToLocal(usaUrl, localAudioPath);
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
