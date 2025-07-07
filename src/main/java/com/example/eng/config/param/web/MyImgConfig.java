package com.example.eng.config.param.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "web")
public class MyImgConfig {

    private String remoteUrl;

    private String userHeadImgPath;

    private String userHeadImgUrl;

    private String animalImgUrl;

}
