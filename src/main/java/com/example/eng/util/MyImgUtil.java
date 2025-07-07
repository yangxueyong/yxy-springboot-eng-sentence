package com.example.eng.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.example.eng.config.param.web.MyImgConfig;
import com.example.eng.constant.MyConstant;
import lombok.extern.slf4j.Slf4j;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * 图片处理工具
 *
 * @author yxy
 * @date 2025/07/07
 */
@Slf4j
public class MyImgUtil {
    /**
     * 将base64字符串转换为img地址
     *
     * @param myImgConfig {@link MyImgConfig}
     * @param openId      {@link String}
     * @param base64Str   {@link String}
     * @return {@link String}
     */
    public static String buildBase64ToImgSrc(MyImgConfig myImgConfig, String openId, String base64Str) throws IOException {
        String headerImgName = openId + ".png";
        String headerPath = myImgConfig.getUserHeadImgPath() + headerImgName;

        if(base64Str.contains(",")){
            base64Str = base64Str.split(",")[1];
        }
        // 使用Base64解码
        byte[] imageBytes = Base64.decode(base64Str);

        try (FileOutputStream fos = new FileOutputStream(headerPath)) {
            // 将字节写入文件
            fos.write(imageBytes);
            // 刷新流
            fos.flush();
        } catch (IOException e) {
            throw e;
        }
        return myImgConfig.getUserHeadImgUrl() + headerImgName;
    }

    /**
     * 获取动物的图片
     * @param myImgConfig
     * @param imgSrc
     * @return {@link String}
     */
    public static String getAnimalImgSrc(MyImgConfig myImgConfig, String imgSrc){
        if(StrUtil.isEmptyIfStr(imgSrc) || imgSrc.startsWith(MyConstant.HTTP)){
            return imgSrc;
        }
        return myImgConfig.getAnimalImgUrl() + imgSrc;
    }
}
