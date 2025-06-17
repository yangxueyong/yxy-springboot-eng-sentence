package com.example.eng.config.translate;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "youdao")
public class YoudaoConfig {

    private String appKey;

    private String appSecret;
}
