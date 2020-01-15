package com.global.common.utils.tree;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 树形结构处理类
 * @Author: 鲁砚琨
 * @CreateTime: 2020-01-14 下午 03:56
 * @Version: v1.0
 */
@ToString
@Getter
public class TreeNodeHandle<T> {

    private ManyNode<T> root;

    private Map<Long, List<ManyNode<T>>> nodeMap = new ConcurrentHashMap<>();

    public TreeNodeHandle(Long rootNodeId, List<ManyNode<T>> manyNodes) {
        root = new ManyNode<>(rootNodeId);
        init(manyNodes);
    }

    /**
     * 初始化树形节点数据
     * @param manyNodes 所有节点数据集合
     */
    private void init(List<ManyNode<T>> manyNodes) {
        // 校验是否有节点数据
        if (manyNodes == null || manyNodes.size() == 0)
            return;
        // 结构转换
        setNodeMap(manyNodes);
        // 添加子节点
        addChildren(root);
    }

    /**
     * 获取当前节点的所有子节点信息
     * @param nodeId 节点Id
     */
    public List<ManyNode<T>> getChildren(Long nodeId) {
        return nodeMap.get(nodeId);
    }

    /**
     * 添加子节点
     * @param curNode 当前节点
     */
    private void addChildren(ManyNode<T> curNode) {
        List<ManyNode<T>> manyNodes = nodeMap.get(curNode.getNodeId());
        if (manyNodes != null) {
            curNode.setChildren(manyNodes);
            // 当前节点递归寻找子节点
            manyNodes.forEach(this::addChildren);
        }
    }

    /**
     * 设置节点Map
     * @param manyNodes 节点集合转换为map结构
     */
    private void setNodeMap(Collection<ManyNode<T>> manyNodes) {
        manyNodes.forEach(node-> {
            // 获取当前节点的父级Id
            Long parentId = node.getParentId();
            List<ManyNode<T>> nodeList = nodeMap.get(parentId);
            // 校验当前节点是否已存在集合，不存在创建
            if (nodeList == null)
                nodeList = new ArrayList<>();
            nodeList.add(node);
            // 把当前节点归档到父级Id的集合下
            nodeMap.put(parentId, nodeList);
        });
    }

}
