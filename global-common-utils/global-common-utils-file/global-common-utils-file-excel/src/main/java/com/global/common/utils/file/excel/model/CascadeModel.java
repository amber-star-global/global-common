package com.global.common.utils.file.excel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-07-01 下午 01:53
 * @Version: v1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public final class CascadeModel {

    /**
     * 名称
     */
    private String name;

    /**
     * 子级联集合
     */
    private List<CascadeModel> childModels;
}
