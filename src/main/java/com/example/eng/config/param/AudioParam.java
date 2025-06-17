package com.example.eng.config.param;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "english.sentence.convert.audio")
public class AudioParam {

    /**
     * 美式翻译url
     */
    private String usaUrl;

    /**
     * 英式翻译url
     */
    private String ukUrl;

    /**
     * 本地保存的路径
     */
    private String localPath;

    /**
     * 本地保存的路径
     */
    private String wordLocalPath;
}
