package com.example.eng.config.wechat;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatConfig {

    private String appid;

    private String appSecret;

    private String getTokenUrl;

    private String getUserinfoUrl;
}
