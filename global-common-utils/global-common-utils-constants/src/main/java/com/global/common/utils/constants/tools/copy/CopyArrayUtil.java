package com.global.common.utils.constants.tools.copy;

import java.util.Arrays;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-04-02 上午 11:10
 * @Version: v1.0
 */
public class CopyArrayUtil {

    /**
     * 数组合并
     * @param sourceArr 源数组
     * @param targetArr 目标数组
     * @return 合并后的数组
     */
    public static <T> T[] arrayMerge(final T[] sourceArr, final T[] targetArr) {
        // 源数组长度
        final int curSouArrLen = sourceArr.length;
        // 目标数组长度
        final int curTarArrLen = targetArr.length;
        // 扩容数组
        T[] newTarArr = Arrays.copyOf(targetArr, (curSouArrLen + curTarArrLen));
        /*
         * copy源数组数组到目标数组中
         * @param src 源数组
         * @param srcPos 从源数组的哪个下标开始拿取元素
         * @param dest 目标数组
         * @param destPos 从目标数组的哪个下标位置开始放置源数组的元素
         * @param length 从源数组拿取多少个元素
         */
        System.arraycopy(sourceArr, 0, newTarArr, curTarArrLen, curSouArrLen);
        return newTarArr;
    }
}
