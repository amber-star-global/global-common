package com.gloabl.common.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-08-17 上午 11:54
 * @Version: v1.0
 */
@NoArgsConstructor
@Data
public class PageApiModel {

    /**
     * 当前页
     */
    private Integer currentPage;

    /**
     * 显示条数
     */
    private Integer pageSize;
}
