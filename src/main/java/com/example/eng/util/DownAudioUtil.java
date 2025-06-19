package com.example.eng.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.*;

/**
 * 下载音频
 * @author yxy
 * @date 2024/10/16
 */
@Slf4j
public class DownAudioUtil {
    private static OkHttp3ClientHttpRequestFactory requestFactory = new OkHttp3ClientHttpRequestFactory();
    private static RestTemplate restTemplate = new RestTemplate(requestFactory);

    public static void downAudioToLocalIfNotExist(String url, String fileName) {
        File file = new File(fileName);
        String absolutePath = file.getParentFile().getAbsolutePath();
        if(!new File(absolutePath).exists()){
             new File(absolutePath).mkdirs();
        }
        log.info("开始下载音频-url：{}", url);
        log.info("开始下载音频-local：{}", absolutePath);
        HttpResponse execute = HttpUtil.createGet(url)
                .execute();

        FileUtil.writeBytes(execute.bodyBytes(),file);
    }


    /**
     * 下载音频到本地
     *
     * @param url
     * @param fileName
     */
    public static void downAudioToLocal(String url, String fileName) {
        HttpResponse execute = HttpUtil.createGet(url)
                .execute();

        FileUtil.writeBytes(execute.bodyBytes(),fileName);
    }

    public static void main(String[] args) {
        String url = "https://7072-prod-9gnou9ab31f4ae8d-1364979505.tcb.qcloud.la/eng/00003.mp3";
        String fileName = "/Users/yxy/work/java/demo/yxy-springboot-eng-sentence/yxy-springboot-eng-sentence/tmp/eng/kk.mp3";

        downAudioToLocalIfNotExist(url, fileName);
    }
    /**
     * 写文件到指定路径
     * @param fileName
     * @param bytes
     * @param append
     * @throws IOException
     */
    public static void writeFileByBytes(String fileName, byte[] bytes, boolean append) throws IOException {
        try(OutputStream out = new BufferedOutputStream(new FileOutputStream(fileName, append))){
            out.write(bytes);
        }
    }
}
