package com.example.eng.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import com.example.eng.config.interceptor.UserContext;
import com.example.eng.constant.MyConstant;
import com.example.eng.entity.down.io.DownLoadFileIO;
import com.example.eng.entity.down.vo.DownLoadFileVO;
import com.example.eng.entity.eng.EngSentenceDetail;
import com.example.eng.entity.eng.EngSentenceDetailWordRel;
import com.example.eng.entity.eng.io.EngSentenceDetailWordRelIO;
import com.example.eng.entity.user.User;
import com.example.eng.entity.user.wechat.io.WechatUserTokenIO;
import com.example.eng.mapper.eng.UserMapper;
import com.example.eng.service.DownLoadService;
import com.example.eng.service.EngSentenceDetailService;
import com.example.eng.service.EngSentenceDetailWordRelService;
import com.example.eng.service.UserService;
import com.example.eng.util.DownAudioUtil;
import com.example.eng.util.VerifyUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
* UserImpl
*
* @author yangxueyong
* @date 2024-10-12 17:07:11
*/
@Slf4j
@Service
public class DownLoadServiceImpl implements DownLoadService {

    @Autowired
    private EngSentenceDetailService engSentenceDetailService;

    @Autowired
    private EngSentenceDetailWordRelService engSentenceDetailWordRelService;

    /**
     * 下载音频文件
     * @param io
     * @return {@link DownLoadFileVO}
     */
    @Override
    public DownLoadFileVO downloadFile(DownLoadFileIO io) {
        //检查权限
        ckPermission(io);

        FileInputStream fileInputStream = null;
        try {
            String localFilePath = io.getFilePath() + io.getFileName();
            File file = new File(localFilePath);
            if (file.exists()) {
                fileInputStream = new FileInputStream(localFilePath);
            }else{
                String webLocalFilePath = io.getFileWebLocalPath() + io.getFileName();
                file = new File(webLocalFilePath);
                if(!file.exists()){
                    DownAudioUtil.downAudioToLocalIfNotExist(io.getFileWebUrl() + io.getFileName(), webLocalFilePath);
                }
                fileInputStream = new FileInputStream(webLocalFilePath);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        InputStreamResource resource = new InputStreamResource(fileInputStream);
        return DownLoadFileVO.builder()
                .fileInputStream(fileInputStream)
                .resource(resource)
                .build();
    }


    /**
     * 检查是否有权限下载音频
     * @param io
     */
    private void ckPermission(DownLoadFileIO io) {
        // 如果不是普通用户则不验证
        if(!VerifyUserUtil.verifyUserIsGeneral()){
            return;
        }
        User user = UserContext.getUser();
        String lastStudyMainId = user.getLastStudyMainId();

        if(MyConstant.DATA_TYPE_DETAIL.equals(io.getDataType())){
            EngSentenceDetail engSentenceDetail = engSentenceDetailService.selectByPrimaryKey(io.getDetailId());
            if(!ObjUtil.equals(lastStudyMainId, engSentenceDetail.getMainId())){
                io.setFileName(MyConstant.NO_AUDIO_MP3);
            }
        }else{
            // 想听单词就听吧
            /*List<EngSentenceDetailWordRel> resList = engSentenceDetailWordRelService.selectByIO(EngSentenceDetailWordRelIO.builder()
                    .detailId(io.getDetailId())
                    .wordId(io.getWordId())
                    .mainId(lastStudyMainId)
                    .build());
            if(CollectionUtil.isEmpty(resList)){
                throw new RuntimeException("没有音频数据--");
            }*/
        }
    }
}
