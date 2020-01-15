package com.global.common.utils.tree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 节点对象
 * @Author: 鲁砚琨
 * @CreateTime: 2020-01-14 下午 03:41
 * @Version: v1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Node<T> implements Serializable {

    private static final long serialVersionUID = -1899120331045640258L;

    /**
     * 节点Id
     */
    protected Long nodeId;

    /**
     * 父节点Id
     */
    protected Long parentId;

    /**
     * 节点对象
     */
    protected T object;
}
