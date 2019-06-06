package com.folm.improvePlan.model;

import com.folm.improvePlan.Utils.CreateBigPrime;
import com.folm.improvePlan.Utils.Exponentiation;
import com.folm.improvePlan.Utils.KnowledgeSinature;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * 群成员类
 * @author folm
 */
public class GroupMember {
    private BigInteger k;
    private BigInteger[] idi;
    private GroupCenter gcenter;
    private GroupManager gman;
    private BigInteger delta; //知识签名
    private int[] xy;// 坐标
    private ArrayList<Object[]> recordList;

    /**
     * 根据坐标返回对应索引
     * @return
     */
    private int getNum(){
        int num = (int)Math.pow(2,xy[0]) + xy[1] - 2;
        return num;
    }

    public GroupMember(GroupCenter gc, int[] xy, ArrayList<Object[]> recordList){
        // 传入群中心，然后可以在群成员中使用群中心公布的内容
        this.gcenter = gc;
        gman = gc.getGmanager();
        k = new CreateBigPrime().getPrime(100);
        BigInteger g = gc.getG();
        idi = new Exponentiation().expMode(g,k);
        delta = new KnowledgeSinature().spk(idi, g);
        this.xy = xy;
        this.recordList= recordList;
    }

    /**
     * 用户获取自身的路径上的节点路径
     * @return
     */
    public ArrayList<Object[]> getPathNodeList(){
        int num = getNum();
        ArrayList<Object[]> arrayList = (ArrayList<Object[]>)gcenter.getPathNodeList(num)[1];
        return arrayList;
    }

    /**
     * 获取存在Si中的那个节点的信息
     * @return
     */
    public Object[] getSiRecord(){
        // 获取num
        int num = getNum();
        int flag = 0;
        ArrayList<Object[]> siRecord = (ArrayList<Object[]>)gcenter.getPathNodeList(num)[1];
        int size = siRecord.size();
        BigInteger c = gcenter.getCRTC();
        for(int i=0;i<size;i++){
            Object[] arr = siRecord.get(i);
            BigInteger yk = (BigInteger) arr[1];
            BigInteger pk = (BigInteger) arr[2];
            BigInteger ykp = c.mod(pk);
            if(yk.compareTo(ykp) == 0){
                flag = i;
                break;
            }
        }
        Object[] res = siRecord.get(flag);
        return res;
    }

    /**
     * 群成员获取自己的成员证书
     * @return
     */
    private Object[] getCertificate(){
        // 获取 rc sc
        BigInteger[] rcSc = gcenter.getrcSc(idi);
        BigInteger rc = rcSc[0];
        BigInteger sc = rcSc[1];
        BigInteger[] wg = gman.getWg(idi);
        BigInteger[] rcdc = gcenter.getrcdc(idi);
        Object[] res = {rc, rcdc, sc, wg, recordList};
        return res;
    }

    /**
     * 成员签名
     * @param msg
     * @return
     */
    public Object[] signMsg(String msg){
        // 获取群成员的成员证书
        Object[] res = getCertificate();
        BigInteger sc = (BigInteger)res[2];
        // 获取 wg 相关信息
        BigInteger[] wg = (BigInteger[])res[3];
        BigInteger wgbase = wg[0];
        BigInteger wgexpo = wg[1];
        // 生成两个beta
        BigInteger beta1 = new CreateBigPrime().getPrime(100);
        BigInteger beta2 = new CreateBigPrime().getPrime(100);
        // 对消息进行哈希加密
        BigInteger hashmsg = GroupCenter.MyHash(msg);
        // 获取公开的信息
        BigInteger g = gcenter.getG();
        BigInteger nc = gcenter.getNc();
        BigInteger eg = gman.getEg();
        BigInteger ng = gman.getNg();
        // 计算z1，z2
        BigInteger z1 = new Exponentiation().expMode(g, hashmsg, nc);
        BigInteger z2 = (new Exponentiation().expMode(beta2,eg,ng)).multiply(new Exponentiation().expMode(g,beta1,ng)).mod(ng);

        // 计算 u
        BigInteger u = GroupCenter.MyHash(z1, z2, msg);
        BigInteger r1 = beta1.add(u.multiply(k.add(sc)));
        Object[] r2 = {beta2, wgbase, u, wgexpo, ng};
        Object[] oarray = this.getSiRecord();
        BigInteger[] gxk = (BigInteger[])oarray[0];

        BigInteger pk = (BigInteger)oarray[2];
        BigInteger[] pkdc = (BigInteger[])oarray[3];
        BigInteger[] r3 = new Exponentiation().expModeArr(gxk[0], gxk[1].multiply(GroupCenter.MyHash(msg)),nc);
        Object[] r0 = {r3,beta1};
        Object[] signRes = {msg, u, r1, r2, r0, pk, pkdc, idi};

        return signRes;
    }

    public int[] getXy() {
        return xy;
    }

    public BigInteger[] getIdi() {
        return idi;
    }

    public BigInteger getUserId(){
        BigInteger ng = gman.getNg();
        return new Exponentiation().expMode(idi[0],idi[1],ng);
    }

    /**
     * 用户自己验证是否可以入群
     * @return
     */
    public boolean isLegal(){
        BigInteger[] wgarr = gman.getWg(idi);
        BigInteger rc = gcenter.getRc(idi);
        BigInteger wgbase = wgarr[0];
        BigInteger wgexponent = wgarr[1];
        BigInteger eg = gman.getEg();
        BigInteger ng = gman.getNg();
        BigInteger idis = new Exponentiation().expMode(idi[0],idi[1],ng);
        BigInteger hashMsg = GroupCenter.MyHash(String.valueOf(idis));
        BigInteger yc = gcenter.getYc();
        BigInteger idg = gman.getIdg();
        BigInteger left = new Exponentiation().expMode(wgbase,eg.negate().multiply(wgexponent),ng).mod(ng);
        System.out.println(left);
        System.out.println("============是不是？？？======================");
        BigInteger right = idg.multiply(rc).multiply(new Exponentiation().expMode(yc, rc.multiply(hashMsg),ng)).multiply(idis).mod(ng);
        System.out.println(right);
        // 无法解决的双重大整数求幂模
        if(left.compareTo(right) == 0){
            return true;
        }
        return false;
    }
}
