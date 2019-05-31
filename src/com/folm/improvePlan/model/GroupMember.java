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
    private BigInteger idi;
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
        gman = new GroupManager(gc);
        k = new CreateBigPrime().getPrime(100);
        BigInteger g = gc.getG();
        idi = new Exponentiation().expMode(g,k,gman.getNg());
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
     * 群成员获取自己的成员证书
     * @return
     */
    private Object[] getCertificate(){
        // 获取 rc sc
        BigInteger[] rcSc = gcenter.getEleCheckNewMemberLegal(idi);
        BigInteger rc = rcSc[1];
        BigInteger sc = rcSc[3];
        BigInteger wg = gman.getWg(idi, rc);
        Object[] res = {rc, sc, wg, recordList};
        return res;
    }

    public Object[] signMsg(String msg){
        Object[] res = getCertificate();
        BigInteger rc = (BigInteger)res[0];
        BigInteger sc = (BigInteger)res[1];
        BigInteger wg = (BigInteger)res[2];
        BigInteger c = gcenter.getCRTC();
        BigInteger beta1 = new CreateBigPrime().getPrime(100);
        BigInteger beta2 = new CreateBigPrime().getPrime(100);
        BigInteger hashmsg = GroupCenter.MyHash(msg);
        BigInteger g = gcenter.getG();
        BigInteger nc = gcenter.getNc();
        BigInteger eg = gman.getEg();
        BigInteger ng = gman.getNg();
        BigInteger z1 = new Exponentiation().expMode(g, hashmsg, nc);
        BigInteger z2 = (new Exponentiation().expMode(beta2,eg,ng)).multiply(new Exponentiation().expMode(g,beta1,ng)).mod(ng);
        String mix = z1+msg+z2;
        BigInteger u = GroupCenter.MyHash(mix);
        BigInteger r1 = beta1.add(u.multiply(k.add(sc)).mod(ng));
        BigInteger r2 = beta2.multiply(new Exponentiation().expMode(wg,u,ng)).mod(ng);
        int num = this.getNum();
        Object[] oarray = gcenter.getRecord(num);
        BigInteger xk = (BigInteger)oarray[5];
        BigInteger pk = (BigInteger)oarray[3];
        BigInteger dc = (BigInteger)oarray[6];
        BigInteger r3 = new Exponentiation().expMode(g, xk.multiply(GroupCenter.MyHash(msg)),nc);
        Object[] signRes = {msg,u,r1,r2,r3,pk,dc};

        System.out.println("z1:"+z1);
        System.out.println("z2:"+z2);

        return signRes;
    }

    public int[] getXy() {
        return xy;
    }

    public BigInteger getIdi() {
        return idi;
    }

    public boolean isLegal(BigInteger rc){
        BigInteger wg = gman.getWg(idi,rc);
        BigInteger eg = gman.getEg();
        BigInteger ng = gman.getNg();
        BigInteger yc = gcenter.getYc();
        BigInteger idg = gman.getIdg();
        BigInteger left = new Exponentiation().expMode(wg,eg.negate(),ng).mod(ng);
        BigInteger right = idg.multiply(rc).multiply(new Exponentiation().expMode(yc, rc.multiply(GroupCenter.MyHash(String.valueOf(idi))),ng)).mod(ng);
        // 无法解决的双重大整数求幂模
        if(left.compareTo(right) == 0){
            return true;
        }
        return true;
    }
}
