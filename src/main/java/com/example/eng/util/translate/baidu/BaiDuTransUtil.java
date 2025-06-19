package com.example.eng.util.translate.baidu;


import com.alibaba.fastjson2.JSON;
import com.example.eng.config.param.translate.BaiduConfig;

public class BaiDuTransUtil {

    // 在平台申请的APP_ID 详见 https://fanyi-api.baidu.com/api/trans/product/desktop?req=developer

    private static BaiDuTransApi BAIDU_TRANS_API = null;

    /**
     * 百度翻译
     * @param config
     * @param query
     * @return {@link BaiDuTransEntity}
     */
    public static BaiDuTransEntity translate(BaiduConfig config, String query) {
        if(BAIDU_TRANS_API == null){
            BAIDU_TRANS_API = new BaiDuTransApi(config.getAppKey(), config.getAppSecret());
        }

        String transResult = BAIDU_TRANS_API.getTransResult(query, "en", "zh");
        BaiDuTransEntity baiDuTransEntity = JSON.parseObject(transResult, BaiDuTransEntity.class);
        return baiDuTransEntity;
    }



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
