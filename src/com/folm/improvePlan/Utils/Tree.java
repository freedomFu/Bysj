package com.folm.improvePlan.Utils;

/**
 * 树 抽象接口
 * @param <T> 树中的数据类型
 * @author folm
 */
public interface Tree<T> {
    /**
     * 寻找左子树
     * @param parentNode 父节点
     * @return
     */
    T leftTree(T parentNode);

    /**
     * 寻找右子树
     * @param parentTree 父节点
     * @return
     */
    T rightTree(T parentNode);

    /**
     * 找父节点
     * @param target
     * @return
     */
    T findParent(T target);

    /**
     * 找兄弟节点
     * @param target
     * @return
     */
    T findBrother(T target);

    /**
     * 添加节点
     * @param data
     */
    void add(T data);

    /**
     * 打印树
     */
    void printTree();

    /**
     * 求 树的深度
     * @return
     */
    int depth();

}
