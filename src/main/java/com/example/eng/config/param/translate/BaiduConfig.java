package com.example.eng.config.param.translate;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "baidu")
public class BaiduConfig {

    private String appKey;

    private String appSecret;
}
