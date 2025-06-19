package com.example.eng.service.impl;

import com.example.eng.entity.down.io.DownLoadFileIO;
import com.example.eng.entity.down.vo.DownLoadFileVO;
import com.example.eng.entity.user.User;
import com.example.eng.entity.user.wechat.io.WechatUserTokenIO;
import com.example.eng.mapper.eng.UserMapper;
import com.example.eng.service.DownLoadService;
import com.example.eng.service.UserService;
import com.example.eng.util.DownAudioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
* UserImpl
*
* @author yangxueyong
* @date 2024-10-12 17:07:11
*/
@Slf4j
@Service
public class DownLoadServiceImpl implements DownLoadService {


    @Override
    public DownLoadFileVO downloadFile(DownLoadFileIO io) {
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
}
