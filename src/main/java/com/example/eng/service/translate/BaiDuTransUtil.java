package com.example.eng.service.translate;


import com.alibaba.fastjson2.JSON;

public class BaiDuTransUtil {

    // 在平台申请的APP_ID 详见 https://fanyi-api.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "20230608001704570";
    private static final String SECURITY_KEY = "AVvcyep28uM6AKpkjFTn";

    private static final BaiDuTransApi BAIDU_TRANS_API = new BaiDuTransApi(APP_ID, SECURITY_KEY);

    /**
     * 百度翻译
     * @param query
     * @return {@link BaiDuTransEntity}
     */
    public static BaiDuTransEntity translate(String query, String from, String to) {
        String transResult = BAIDU_TRANS_API.getTransResult(query, from, to);
        BaiDuTransEntity baiDuTransEntity = JSON.parseObject(transResult, BaiDuTransEntity.class);
        return baiDuTransEntity;
    }

    public static void main(String[] args) {
        System.out.println(BaiDuTransUtil.translate("the","en","zh"));
    }
}
