package com.global.common.web.utils.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2021-01-16 下午 04:11
 * @Version: v1.0
 */
@NoArgsConstructor
@Data
public class SendMessageOperateModel implements Serializable {

    private static final long serialVersionUID = -7581524228893365124L;

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
     * 当前操作角色
     */
    protected Integer currentRole;
}
