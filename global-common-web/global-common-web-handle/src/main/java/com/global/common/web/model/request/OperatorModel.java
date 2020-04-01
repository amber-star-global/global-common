package com.global.common.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

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
    protected Long operatorId = 1L;
    /**
     * 操作人手机号
     */
    protected String phone = "13000000000";
    /**
     * 操作人名称
     */
    protected String name = "admin";
    /**
     * 当前角色
     */
    protected List<String> currentRole;
}
