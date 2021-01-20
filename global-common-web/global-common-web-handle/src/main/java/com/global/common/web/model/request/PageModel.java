package com.global.common.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 列表分页model
 *
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

    private static final long serialVersionUID = -8545275872884768674L;

    /**
     * 当前页
     */
    @Min(value = 0, message = "当前页必须大于0")
    @NotNull(message = "当前页不能为空")
    protected Integer currentPage;

    /**
     * 显示条数
     */
    @Min(value = 0, message = "显示条数必须大于0")
    @NotNull(message = "显示条数不能为空")
    protected Integer pageSize;
}
