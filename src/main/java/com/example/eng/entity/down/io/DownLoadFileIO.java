package com.example.eng.entity.down.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 下载音频的入参
 * @author yxy
 * @date 2025/06/20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DownLoadFileIO {

    /**
     * 本地音频存储的路径
     */
    private String filePath;
    /**
     * 文件的名字
     */
    private String fileName;

    /**
     * 远端文件所在的url
     */
    private String fileWebUrl;
    /**
     * 远端文件下载之后存放在哪个位置
     */
    private String fileWebLocalPath;

    /**
     * 当前的数据类型
     */
    private String dataType;

    /**
     * 当前的数据id
     */
    private String detailId;

    /**
     * 当前的单词id
     */
    private String wordId;
}
