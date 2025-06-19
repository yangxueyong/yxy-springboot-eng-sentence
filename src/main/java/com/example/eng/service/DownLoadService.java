package com.example.eng.service;

import com.example.eng.entity.down.io.DownLoadFileIO;
import com.example.eng.entity.down.vo.DownLoadFileVO;
import com.example.eng.entity.user.User;
import com.example.eng.entity.user.wechat.io.WechatUserTokenIO;
import org.springframework.core.io.InputStreamResource;

import java.io.FileInputStream;

/**
* 下载相关
*
* @author yangxueyong
* @date 2024-10-12 17:07:11
*/
public interface DownLoadService {
    DownLoadFileVO downloadFile(DownLoadFileIO io);
}
