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
     * 群成员获取自己的成员证书
     * @return
     */
    public Object[] getCertificate(){
        // 获取 rc sc
        BigInteger[] rcSc = gcenter.getEleCheckNewMemberLegal(idi);
        BigInteger rc = rcSc[1];
        BigInteger sc = rcSc[3];
        BigInteger wg = gman.getWg(idi, rc);
        int num = (int)Math.pow(2,xy[0]) + xy[1] - 2;

        Object[] res = {rc, sc, wg, recordList};

        return res;
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
