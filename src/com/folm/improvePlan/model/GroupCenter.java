package com.folm.improvePlan.model;

import com.folm.improvePlan.Utils.CreateBigPrime;
import com.folm.improvePlan.Utils.Exponentiation;
import com.folm.improvePlan.Utils.GCD;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
    private List<GroupMember> memberRecordList = new ArrayList<>();
    private GroupManager gmanager = new GroupManager(this);

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
        // 生成g
        g = new CreateBigPrime().getPrime(500);
        // 私钥
        xc = new CreateBigPrime().getPrime(500);
        // 生成ec
        ec = new BigInteger("65537");
        // 生成yc
        yc = new Exponentiation().expMode(g, xc, nc);
        // 计算dc  有点问题
        dc = new GCD().getInverseEle(ec,fy);
        System.out.println("before:"+dc);
        // 创建子树 此时还没有初始化群成员
//        sbtree.create();
        System.out.println("after:"+dc);
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

    public Object[] getEleCheckNewMemberLegal(BigInteger idi){
        BigInteger alpha = new CreateBigPrime().getPrime(100);
        BigInteger ng = gmanager.getNg();
        BigInteger rc = new Exponentiation().expMode(g,alpha,ng);
        BigInteger sc = alpha.add((rc.multiply(MyHash(String.valueOf(idi)))));//删除了xc ，试一试 剩下的能不能解决

        BigInteger left = new Exponentiation().expMode(g,sc,ng);
        BigInteger right = rc.multiply(new Exponentiation().expMode(g,rc.multiply(MyHash(String.valueOf(idi))),ng)).mod(ng);

        System.out.println(left);
        System.out.println("=======================================");
        System.out.println(right);

        BigInteger left1 = rc.mod(nc);
        BigInteger right1 = new Exponentiation().expMode(rc,dc.multiply(ec),nc);
        System.out.println(left1);
        System.out.println("=======================================");
        System.out.println(right1);

        return null;
    }

    public BigInteger getIdc() {
        return idc;
    }

    public BigInteger getG() {
        return g;
    }

    public BigInteger getNc() {
        return nc;
    }

    public BigInteger getYc() {
        return yc;
    }

    public BigInteger getEc() {
        return ec;
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
     * 成员加入
     * @return
     */
    public boolean addMember(){
        int h = (int)Math.ceil(this.log(defaultData.length, 2));
        int length = memberRecordList.size();
        int num = (int)Math.pow(2, h-1) - 1 + length;
        ArrayList<Object[]> recordData = new ArrayList<>();
        ArrayList<Integer> list = sbtree.getPathByLeafNode(num);

        for(int i=0;i<list.size();i++){
            int index = list.get(i);
            Object[] oArray = sbtree.getMemberRecordData(index);
            recordData.add(oArray);
        }

        if(num<=14){
            int[] xy = sbtree.getXY(num);
            GroupMember gNewmember = new GroupMember(this, xy, recordData);

            if(null!=gNewmember){
                memberRecordList.add(gNewmember);
                return true;
            }
        }

        return false;
    }

    public List<GroupMember> getMemberRecordList() {
        return memberRecordList;
    }

    /**
     * 求对数
     * @param value
     * @param base
     * @return
     */
    private double log(double value, double base) {
        return Math.log(value) / Math.log(base);
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
                coord = this.getXY(i);
                // 初始化树的每一个节点
                yk = new CreateBigPrime().getPrime(100);
                xk = new GCD().getInverseEle(yk,fy);
                pk = new CreateBigPrime().getPrime(110);
                gxk = new Exponentiation().expMode(g, xk, fy);
                pkdc = new Exponentiation().expMode(pk, dc, fy);
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
         * 获取节点所对应记录的内容
         * @param index
         * @return
         */
        public Object[] getMemberRecordData(int index){
            Node node = nodes.get(index);
            return node.getRecordData();
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
         * 根据叶子节点的位置获取到达根节点路径上每一个点的信息 也就是num
         * @param num
         * @return
         */
        public ArrayList<Integer> getPathByLeafNode(int num){
            ArrayList<Integer> pathList = new ArrayList<>();
            int h = (int)log(defaultData.length, 2);
            int[] xy = this.getXY(num);
            pathList.add(num);
            int temp = num;

            while((temp+1)/2 > 1){
                pathList.add((temp+1)/2 - 1);
                temp = (temp+1) / 2 - 1;
            }
            pathList.add(0);
            return pathList;
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

            public Object[] getRecordData() {
                return recordData;
            }
        }
    }
}
