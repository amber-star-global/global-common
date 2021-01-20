package com.global.common.utils.constants.utils.date;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * 日期转换格式
 *
 * @Author: 鲁砚琨
 * @CreateTime: 2021-01-16 下午 06:13
 * @Version: v1.0
 */
@AllArgsConstructor
@Getter
public enum  DatePattern {

    /**
     * Date类型toString格式
     */
    DATE_STRING_PATTERN("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH),

    /**
     * 转换格式: 年-月-日 时:分:秒.毫秒
     */
    DATE_TIME_MILLISECOND_PATTERN("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINESE),
    /**
     * 转换格式: 年-月-日 时:分:秒
     */
    DATE_TIME_PATTERN("yyyy-MM-dd HH:mm:ss", Locale.CHINESE),
    /**
     * 转换格式: 年-月-日
     */
    DATE_PATTERN("yyyy-MM-dd", Locale.CHINESE),
    ;

    private String pattern;

    private Locale locale;

    public static List<String> getDatePatternList() {
        return Arrays.stream(DatePattern.values()).map(DatePattern::getPattern).collect(Collectors.toList());
    }
}
