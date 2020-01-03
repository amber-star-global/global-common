package com.global.common.web.base.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 列表分页model
 * @Author: 鲁砚琨
 * @CreateTime: 2019-12-11 下午 03:10
 * @Version: v1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class PageModel extends OperatorModel {

    private static final long serialVersionUID = -7148850360261421687L;
    /**
     * 当前页
     */
    protected Integer currentPage;
    /**
     * 显示条数
     */
    protected Integer pageSize;
}
