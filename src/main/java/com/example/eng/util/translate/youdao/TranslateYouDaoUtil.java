package com.example.eng.util.translate.youdao;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.eng.config.translate.YoudaoConfig;
import com.example.eng.entity.translate.YoudaoTranslate;
import com.example.eng.entity.translate.io.YoudaoTranslateEn2VoiceIO;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * 网易有道智云翻译服务api调用demo
 * api接口: https://openapi.youdao.com/api
 */

public class TranslateYouDaoUtil {
    /**
     * 将文本转成音频
     * @param config
     * @param io
     * @return {@link YoudaoTranslate}
     * @throws Exception
     */
    public static YoudaoTranslate translateEn2Voice(YoudaoConfig config, YoudaoTranslateEn2VoiceIO io)  {
        // 添加请求参数
        Map<String, String[]> params = createRequestParams(io);
        // 添加鉴权相关参数
        try {
            AuthV3Util.addAuthParams(config.getAppKey(), config.getAppSecret(), params);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        // 请求api服务
        byte[] result = HttpUtil.doPost("https://openapi.youdao.com/api", null, params, "application/json");
        // 打印返回结果
        if (result != null) {
            String x = new String(result, StandardCharsets.UTF_8);
            String s= JSON.toJSONString(
                    JSONObject.parseObject(x), SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteDateUseDateFormat
            );

            return JSONObject.parseObject(s, YoudaoTranslate.class);
        }
        return null;
    }

    /**
     * 组装参数
     * @param io
     * @return {@link Map}<{@link String}, {@link String[]}>
     */
    private static Map<String, String[]> createRequestParams(YoudaoTranslateEn2VoiceIO io) {
        /*
         * note: 将下列变量替换为需要请求的参数
         * 取值参考文档: https://ai.youdao.com/DOCSIRMA/html/%E8%87%AA%E7%84%B6%E8%AF%AD%E8%A8%80%E7%BF%BB%E8%AF%91/API%E6%96%87%E6%A1%A3/%E6%96%87%E6%9C%AC%E7%BF%BB%E8%AF%91%E6%9C%8D%E5%8A%A1/%E6%96%87%E6%9C%AC%E7%BF%BB%E8%AF%91%E6%9C%8D%E5%8A%A1-API%E6%96%87%E6%A1%A3.html
         */
        String q = io.getQuery();
        String from = "en";
        String to = "zh-CHS";
//        String vocabId = "您的用户词表ID";

        return new HashMap<String, String[]>() {{
            put("q", new String[]{q});
            put("from", new String[]{from});
            put("to", new String[]{to});
//            put("vocabId", new String[]{vocabId});
        }};
    }
}
