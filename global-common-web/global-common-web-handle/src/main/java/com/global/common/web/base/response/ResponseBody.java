package com.global.common.web.base.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2019-12-11 下午 03:23
 * @Version: v1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseBody<T> implements Serializable {

    private static final long serialVersionUID = 6736807279059400197L;
    /**
     * 返回数据
     */
    private T responseObject;
}
