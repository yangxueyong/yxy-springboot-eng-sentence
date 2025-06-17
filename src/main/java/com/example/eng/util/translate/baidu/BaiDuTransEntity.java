package com.example.eng.util.translate.baidu;

import lombok.Data;

import java.util.List;

/**
 *
 * {"from":"zh","to":"en","trans_result":[{"src":"\u9ad8\u5ea6600\u7c73","dst":"Height 600 meters"}]}
 *
 * @author yxy
 * @date 2024/10/16
 */
@Data
public class BaiDuTransEntity {
    /**
     * 数据源类型
     */
    private String from;

    /**
     * 目标语言类型
     */
    private String to;

    /**
     * 翻译结果
     */
    private List<TransResult> trans_result;
    @Data
    public static class TransResult {
        /**
         * 元数据
         */
        private String src;
        /**
         * 翻译结果
         */
        private String dst;
    }
}
