package com.global.common.web.base.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 操作人信息model
 * @Author: 鲁砚琨
 * @CreateTime: 2019-12-11 下午 03:11
 * @Version: v1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OperatorModel implements Serializable {

    private static final long serialVersionUID = 1925646412165535560L;
    /**
     * 操作人Id
     */
    protected Long operatorId;
    /**
     * 操作人手机号
     */
    protected String phone;
    /**
     * 操作人名称
     */
    protected String name;
    /**
     * 当前角色, 多角色用','拼接
     */
    protected String currentRole;
}
