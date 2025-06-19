package com.example.eng.config.param.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * oss.wx-back-eng-local-path=./tmp/eng/
 * oss.wx-back-eng-word-local-path=./tmp/eng-word/
 */
@Data
@Component
@ConfigurationProperties(prefix = "oss")
public class OssConfig {
    private String wxBackEngUrl;

    private String wxBackEngWordUrl;

    private String wxBackEngLocalPath;

    private String wxBackEngWordLocalPath;
}
