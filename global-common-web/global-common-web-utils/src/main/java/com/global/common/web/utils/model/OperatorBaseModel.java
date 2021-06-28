package com.global.common.web.utils.model;

import com.global.common.utils.constants.utils.VerifyProxyUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 操作人信息model
 *
 * @Author: 鲁砚琨
 * @CreateTime: 2019-12-11 下午 03:11
 * @Version: v1.0
 */
@Data
public class OperatorBaseModel implements Serializable {

    private static final long serialVersionUID = 7787687954001072005L;

    public OperatorBaseModel() {
        init();
    }

    public OperatorBaseModel(Long operatorId, String name, String phone, Integer currentRole, List<Integer> roles) {
        if (VerifyProxyUtil.isNull(operatorId) && VerifyProxyUtil.isNull(name) && VerifyProxyUtil.isNull(phone)
                && VerifyProxyUtil.isNull(currentRole) && VerifyProxyUtil.isEmpty(roles)) {
            // 如果参数全部为null, 则初始化默认参数
            init();
        }
        this.operatorId = operatorId;
        this.name = name;
        this.phone = phone;
        this.currentRole = currentRole;
        this.roles = roles;
    }

    /**
     * 操作人Id
     */
    protected Long operatorId;

    /**
     * 操作人名称
     */
    protected String name;

    /**
     * 操作人手机号
     */
    protected String phone;

    /**
     * 当前操作角色
     */
    protected Integer currentRole;

    /**
     * 操作人角色
     */
    protected List<Integer> roles;

    /**
     * 初始化参数
     */
    private void init() {
        this.operatorId = 0L;
        this.name = "admin";
        this.phone = "10000000000";
        this.currentRole = 0;
        this.roles = null;
    }
}
