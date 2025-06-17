package com.example.eng.entity.translate.io;


import lombok.Builder;
import lombok.Data;

/**
 * 有道翻译
 * @author yxy
 * @date 2025/06/17
 */
@Data
@Builder
public class YoudaoTranslateEn2VoiceIO {
    /**
     * 原始文本
     */
    private String query;

}
