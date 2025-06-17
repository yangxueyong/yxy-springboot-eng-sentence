package com.example.eng.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 下载音频
 * @author yxy
 * @date 2024/10/16
 */
public class DownAudioUtil {
    private static OkHttp3ClientHttpRequestFactory requestFactory = new OkHttp3ClientHttpRequestFactory();
    private static RestTemplate restTemplate = new RestTemplate(requestFactory);

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
        String url = "https://openapi.youdao.com/ttsapi?q=how+are+you+today%3F&langType=en-USA&sign=C17F20AF54E50A495A0E77D1B0E12DD6&salt=1750126172470&voice=4&format=mp3&appKey=4250569f7b9f3b38&ttsVoiceStrict=false&osType=api";
        String fileName = "/Users/yxy/tmp/eng/audio.mp3";
        HttpResponse execute = HttpUtil.createGet(url)
                .execute();

        FileUtil.writeBytes(execute.bodyBytes(),fileName);

//        downAudioToLocal(url, fileName);
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
