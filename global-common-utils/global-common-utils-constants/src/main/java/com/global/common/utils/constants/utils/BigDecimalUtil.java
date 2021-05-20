package com.global.common.utils.constants.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-01-15 下午 04:05
 * @Version: v1.0
 */
public class BigDecimalUtil {

    @AllArgsConstructor
    @Getter
    enum Repair {
        // 进位
        UP(BigDecimal.ROUND_UP),
        // 截取, 剩下的数据删除
        CUT(BigDecimal.ROUND_DOWN),
        // 四舍五入
        HALF_UP(BigDecimal.ROUND_HALF_UP);

        private int code;
    }

    /**
     * 默认保留2为小数
     */
    public static final int DEFAULT_RETAIN_DIGIT = 2;

    /**
     * 默认保留精度
     */
    public static final Repair DEFAULT_RETAIN_ACCURACY = Repair.HALF_UP;

    /**
     * 校验值是否为空
     *
     * @param value 处理数据
     */
    private static boolean isValue(final BigDecimal value) {
        return VerifyProxyUtil.nonNull(value);
    }

    /**
     * 保留数据位数并做补偿处理
     *
     * @param value  处理数据
     * @param scale  保留位数
     * @param repair 补位, 向上取整或是向下取整
     */
    public static BigDecimal scale(BigDecimal value, final int scale, Repair repair) {
        return isValue(value) ? value.setScale(scale, repair.getCode()) : null;
    }

    /**
     * 指定保留位数,做四舍五入处理
     *
     * @param value 处理数据
     * @param scale 保留位数
     */
    public static BigDecimal scale(BigDecimal value, final int scale) {
        return scale(value, scale, DEFAULT_RETAIN_ACCURACY);
    }

    /**
     * 加运算
     *
     * @param values 处理数据
     */
    public static BigDecimal add(BigDecimal... values) {
        return add(DEFAULT_RETAIN_DIGIT, values);
    }

    /**
     * 加运算
     *
     * @param values 处理数据
     */
    public static BigDecimal add(final int scale, BigDecimal... values) {
        return add(scale, DEFAULT_RETAIN_ACCURACY, values);
    }

    /**
     * 加运算
     *
     * @param values 处理数据
     */
    public static BigDecimal add(final int scale, final Repair repair, BigDecimal... values) {
        return VerifyProxyUtil.isEmpty(values) ? BigDecimal.ZERO :
                scale(Arrays.stream(values).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add), scale, repair);
    }


    /**
     * 减运算
     *
     * @param values 处理数据
     */
    public static BigDecimal subtract(BigDecimal... values) {
        return subtract(DEFAULT_RETAIN_DIGIT, values);
    }

    /**
     * 减运算
     *
     * @param values 处理数据
     */
    public static BigDecimal subtract(final int scale, BigDecimal... values) {
        return subtract(scale, DEFAULT_RETAIN_ACCURACY, values);
    }

    /**
     * 减运算
     *
     * @param values 处理数据
     */
    public static BigDecimal subtract(final int scale, final Repair repair, BigDecimal... values) {
        return VerifyProxyUtil.isEmpty(values) ? BigDecimal.ZERO :
                scale(Arrays.stream(values).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::subtract), scale, repair);
    }

    /**
     * 乘运算
     *
     * @param values 处理数据
     */
    public static BigDecimal multiply(BigDecimal... values) {
        return multiply(DEFAULT_RETAIN_DIGIT, values);
    }

    /**
     * 乘运算
     *
     * @param values 处理数据
     */
    public static BigDecimal multiply(final int scale, BigDecimal... values) {
        return multiply(scale, DEFAULT_RETAIN_ACCURACY, values);
    }

    /**
     * 乘运算
     *
     * @param values 处理数据
     */
    public static BigDecimal multiply(final int scale, final Repair repair, BigDecimal... values) {
        return VerifyProxyUtil.isEmpty(values) ? BigDecimal.ZERO : Arrays.stream(values)
                .filter(Objects::nonNull).reduce((b1, b2) -> b1.multiply(b2).setScale(scale, repair.getCode())).orElse(null);
    }

    /**
     * 除运算，除数为0会抛异常
     *
     * @param values 处理数据
     */
    public static BigDecimal divide(BigDecimal... values) {
        return divide(DEFAULT_RETAIN_DIGIT, values);
    }

    /**
     * 除运算，除数为0会抛异常
     *
     * @param values 处理数据
     */
    public static BigDecimal divide(final int scale, BigDecimal... values) {
        return divide(scale, DEFAULT_RETAIN_ACCURACY, values);
    }

    /**
     * 除运算，除数为0会抛异常
     *
     * @param values 处理数据
     */
    public static BigDecimal divide(final int scale, final Repair repair, BigDecimal... values) {
        return VerifyProxyUtil.isEmpty(values) ? BigDecimal.ZERO : Arrays.stream(values)
                .filter(Objects::nonNull).reduce((b1, b2) -> b1.divide(b2, scale, repair.getCode())).orElse(null);
    }
}
