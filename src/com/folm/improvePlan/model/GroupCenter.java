package com.folm.improvePlan.model;

import com.folm.improvePlan.Utils.CreateBigPrime;
import com.folm.improvePlan.Utils.Exponentiation;
import com.folm.improvePlan.Utils.GCD;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

/**
 * 群中心
 * 群中心的类，完成群中心的一系列操作
 * 群中心的初始化 生成ID 大素数 以及 声明 哈希函数
 * 完成 完全二叉树的设计
 * 后续内容要放到 添加群成员操作之后才能完成
 * 把 每个用户的信息 给每个用户获取
 * 然后 最后通过中国剩余定理 获取最终的C
 * @author folm
 */
public class GroupCenter {
    private BigInteger idc;
    private BigInteger q1;
    private BigInteger q2;
    private BigInteger fy;
    private BigInteger g;
    private BigInteger nc;
    private BigInteger xc;
    private BigInteger yc;
    private BigInteger ec;
    private BigInteger dc;
    private int[] defaultData = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
    private Subtree sbtree = new Subtree(defaultData);

    /**
     * 构造方法
     * 用于初始化群中心的身份 IDc,以及生成大素数q1,q2,以及选择出来的g
     */
    public GroupCenter(){
        // 初始化群中心的身份 IDc  时间戳的平方
        idc = new BigInteger(String.valueOf(System.currentTimeMillis())).pow(2);
        // 获取两个大素数  以及  欧拉函数（求 素数 个数）
        q1 = new CreateBigPrime().getPrime(1024);
        q2 = new CreateBigPrime().getPrime(1024);
        fy = q1.subtract(BigInteger.ONE).multiply(q2.subtract(BigInteger.ONE));
        // 计算乘积
        nc = q1.multiply(q2);
        // 生成k
        g = new CreateBigPrime().getPrime(512);
        // 私钥
        xc = new CreateBigPrime().getPrime(512);
        // 生成ec
        ec = new BigInteger("65537");
        // 生成yc
        yc = new Exponentiation().expMode(g, xc, nc);
        // 计算dc  有点问题
        dc = new GCD().getInverseEle(ec,fy);
//        System.out.println("before:"+dc);
        // 创建子树 此时还没有初始化群成员
        sbtree.create();
//        System.out.println("after:"+dc);
//        System.out.println("构造方法");
    }

    /**
     * 返回群中心要公开的东西
     * 除了公私钥之外 还有哈希函数也是要公开的，但是这里函数不可以传入数组中
     * 所以，我选择在下方声明了静态的哈希函数方法来处理
     * 群中心初始化中 公开的内容
     * @return 返回值为 nc,yc,ec,g,hx,idc
     */
    public BigInteger[] getGroupInfo(){
        BigInteger[] res = {nc, yc, ec, g, idc};
        return res;
    }

    /**
     * 使用 SHA-256哈希加密 并把加密之后的信息转换成BigInteger
     * @param msg
     * @return
     */
    public static BigInteger MyHash(String msg){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(msg.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        BigInteger res = new BigInteger(generatedPassword, 16);
        return res;
    }

    /**
     * 内部类 创建子树
     * 这里创建的实际上是 满二叉树
     * 应该根据注册的用户数目来创建的
     * 默认创建4层，最后一层都是叶节点  也就是这里一共有八个叶子节点
     */
    private class Subtree {
        List<Node> nodes = null;
        private int[] datas;
        private int num; //节点数目

        public Subtree(int[] datas){
            this.datas = datas;
            this.num = this.datas.length;
        }

        public void create() {
            nodes = new LinkedList<>();
            int[] coord;
            BigInteger yk;
            BigInteger xk;
            BigInteger pk;
            BigInteger gxk;
            BigInteger pkdc;

            for(int i=0;i<this.num;i++) {
                coord = this.getXY(num);
                // 初始化树的每一个节点
                yk = new CreateBigPrime().getPrime(510);
                xk = new GCD().getInverseEle(yk,fy);
                pk = new CreateBigPrime().getPrime(512);
                gxk = new Exponentiation().expMode(g, xk, nc);
                pkdc = new Exponentiation().expMode(pk, dc, nc);
                Object[] recordData = {coord, gxk, yk, pk, pkdc};
                nodes.add(new Node(recordData, datas[i]));
            }
            // 如果父节点编号为x，那么左子节点的编号是2x，右子节点的编号是2x+1
            for(int nodeId=1;nodeId<=this.num/2;nodeId++) {
                // 索引从0开始，需要在节点编号上减1
                nodes.get(nodeId-1).leftChild = nodes.get(nodeId*2-1);
                if(nodeId*2 < this.num) {
                    nodes.get(nodeId-1).rightChild = nodes.get(nodeId*2);
                }
            }
        }

        /**
         * 根据编号 获取位置坐标
         * @param num
         * @return
         */
        private int[] getXY(int num){
            int[] res = {0,0};
            if(num>0){
                int x = (int)Math.floor(this.log(num+1, 2));
                int start = (int)Math.pow(2,x);
                int y = (num+1)-start+1;
                res[0] = x;
                res[1] = y;
            }
            return res;
        }

        /**
         * 获取对数值
         * @param value
         * @param base
         * @return
         */
        private double log(double value, double base) {
            return Math.log(value) / Math.log(base);
        }

        /**
         * 子节点
         */
        private class Node{
            Node leftChild;
            Node rightChild;
            Object[] recordData;
            int data;

            Node(Object[] recordData, int data){
                this.recordData = recordData;
                this.data = data;
            }
        }
    }
}
