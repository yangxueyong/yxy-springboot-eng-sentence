package com.example.eng.util;

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
        ResponseEntity<byte[]> responseEntity = restTemplate.getForEntity(url, byte[].class);
        byte[] body = responseEntity.getBody();
        try {
            writeFileByBytes(fileName,body,false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
