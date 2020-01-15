package com.global.common.utils.tree;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 多节点对象
 * @Author: 鲁砚琨
 * @CreateTime: 2020-01-14 下午 03:59
 * @Version: v1.0
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class ManyNode<T> extends Node<T> {

    private static final long serialVersionUID = -4668099141854063956L;

    /**
     * 子节点集合
     */
    private List<ManyNode<T>> children;

    public ManyNode() {
        this.children = new ArrayList<>();
    }

    public ManyNode(Long nodeId) {
        super.nodeId = nodeId;
        this.children = new ArrayList<>();
    }

    public ManyNode(Long nodeId, Long parentId, T object) {
        super.nodeId = nodeId;
        super.parentId = parentId;
        super.object = object;
        this.children = new ArrayList<>();
    }
}
