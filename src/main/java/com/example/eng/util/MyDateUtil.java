package com.example.eng.util;

import org.apache.commons.lang3.time.DurationFormatUtils;

/**
 * 时间处理器
 * @author yxy
 * @date 2025/07/08
 */
public class MyDateUtil {
    /**
     * 格式化毫秒
     * @param millis
     * @return {@link String}
     */
    public static String formatMillis(long millis) {
        return DurationFormatUtils.formatDuration(millis, "HH:mm:ss.SSS");
    }
}
