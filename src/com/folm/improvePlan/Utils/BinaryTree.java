package com.folm.improvePlan.Utils;

import java.util.Arrays;

/**
 * 完全二叉树
 * @param <T>
 * @author folm
 */
public class BinaryTree<T> implements Tree<T> {
    /**
     * 树的容器 这里使用数组实现树的结构
     */
    private Object[] treeContainer;
    /**
     * 默认容量
     */
    private static int sDefaultLen = 10;
    /**
     * 树的大小
     */
    private int treeSize;

    /**
     * 构造方法 生成容量为10的容器
     */
    public BinaryTree(){
        this(sDefaultLen);
    }

    /**
     * 含参构造
     * @param len
     */
    public BinaryTree(int len){
        treeContainer = new Object[len];
    }

    /**
     * 寻找左子树
     * @param parentNode 父节点
     * @return
     */
    @Override
    public T leftTree(T parentNode) {
        // 找到这个节点的位置
        int index = find(parentNode);

        if(index != -1){
            // 左子树 = 2n+1
            // 右子树 = 2n+2

            // 左子树位置
            int childIndex = index*2+1;
            if(childIndex < treeSize){
                return (T)treeContainer[childIndex];
            }
        }
        return null;
    }

    /**
     * 寻找右子树
     * @param parentNode 父节点
     * @return
     */
    @Override
    public T rightTree(T parentNode) {
        // 找到这个节点的位置
        int index = find(parentNode);

        if(index != -1){
            // 左子树 = 2n+1
            // 右子树 = 2n+2

            // 左子树位置
            int childIndex = index*2+2;
            if(childIndex < treeSize){
                return (T)treeContainer[childIndex];
            }
        }
        return null;
    }

    /**
     * 找父节点
     * @param target
     * @return
     */
    @Override
    public T findParent(T target) {
        // 找这个节点的位置
        int index = find(target);
        // 表示找到了这个节点
        if(index > 0){
            // 先判断这个节点是左子树还是右子树
            // 左子树都为奇数
            // 右子树都为偶数
            boolean isLeft = index % 2 == 1;
            if (isLeft) {
                // 如果是左子树
                return (T) treeContainer[(index - 1) << 1];
            } else {
                // 如果是右子树
                return (T) treeContainer[(index - 2) << 1];
            }
        }

        return null;
    }

    /**
     * 找兄弟节点
     * @param target
     * @return
     */
    @Override
    public T findBrother(T target) {
        // 找这个节点的位置
        int index = find(target);
        // 必须不是根节点
        if(index > 0){
            // 判断是左子树还是右子树
            boolean isLeft = index % 2 == 1;

            // 是左子树
            if (isLeft) {
                // 判断有没有右子树
                if(index+1 < treeSize){
                    return (T)treeContainer[index + 1];
                }
            }else{
                // 有右子树必定有左子树
                return (T)treeContainer[index - 1];
            }
        }
        return null;
    }
    /**
     * 添加节点
     * @param data
     */
    @Override
    public void add(T data) {
        // 判断是否需要扩容
        if(treeSize >= treeContainer.length){
            // 扩容
            resize();
        }

        // 赋值
        treeContainer[treeSize] = data;
        // 长度++
        treeSize++;
    }
    /**
     * 打印树
     */
    @Override
    public void printTree() {
        printTree(0);
    }

    /**
     * 求 树的深度
     * @return
     */
    @Override
    public int depth() {
        return (int) (log(treeSize, 2)+1);
    }

    private void printTree(int index){
        if(index < treeSize) {
            // 根 左 右
            System.out.println(treeContainer[index].toString());
            // 左子树
            printTree((index*2 + 1));
            // 右子树
            printTree((index*2 + 2));
        }
    }

    /**
     * 容器扩容
     */
    private void resize() {
        treeContainer = Arrays.copyOf(treeContainer, treeContainer.length << 1);
    }

    /**
     * 找位置
     * @param data
     * @return
     */
    private int find(T data) {
        for(int i=0;i<treeSize;i++){
            if(data.equals(treeContainer[i])){
                return i;
            }
        }
        return -1;
    }

    public static double log(double value, double base) {
        return Math.log(value) / Math.log(base);
    }
}
