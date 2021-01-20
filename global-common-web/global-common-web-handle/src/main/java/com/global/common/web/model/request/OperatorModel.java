package com.global.common.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 操作人信息model
 *
 * @Author: 鲁砚琨
 * @CreateTime: 2019-12-11 下午 03:11
 * @Version: v1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OperatorModel implements Serializable {

    private static final long serialVersionUID = 7787687954001072005L;

    /**
     * 操作人Id
     */
    protected Long operatorId = 0L;

    /**
     * 操作人手机号
     */
    protected String phone = "10000000000";

    /**
     * 操作人名称
     */
    protected String name = "admin";

    /**
     * 当前操作角色
     */
    protected Integer currentRole;

    /**
     * 操作人角色
     */
    protected List<Integer> roles;
}
