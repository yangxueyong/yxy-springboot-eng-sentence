package com.example.eng.util.translate.baidu;

import java.util.HashMap;
import java.util.Map;

public class BaiDuTransApi {
    private static final String TRANS_API_HOST = "https://fanyi-api.baidu.com/api/trans/vip/translate";

    private String appid;
    private String securityKey;

    public BaiDuTransApi(String appid, String securityKey) {
        this.appid = appid;
        this.securityKey = securityKey;
    }

    public String getTransResult(String query, String from, String to) {
        Map<String, String> params = buildParams(query, from, to);
        return BaiDuHttpGet.get(TRANS_API_HOST, params);
    }

    private Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);

        params.put("appid", appid);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        params.put("sign", BaiDuMD5.md5(src));

        return params;
    }

}
