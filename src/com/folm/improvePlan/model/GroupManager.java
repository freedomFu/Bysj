package com.folm.improvePlan.model;

import com.folm.improvePlan.Utils.CreateBigPrime;
import com.folm.improvePlan.Utils.Exponentiation;
import com.folm.improvePlan.Utils.GCD;

import java.math.BigInteger;

/**
 * 群管理员
 * 没必要建立内部类，可以使用列表来建立联系
 * @author folm
 */
public class GroupManager {
    // 群管理员身份
    private BigInteger idg;
    // 随机选择大素数 以及乘积
    private BigInteger q3;
    private BigInteger q4;
    private BigInteger ng;
    private BigInteger gmfy;
    // 两个大整数
    private BigInteger eg;
    private BigInteger dg;
    private GroupCenter gcenter;

    public GroupManager(GroupCenter gc){
        // 传入群中心，然后可以在群成员中使用群中心公布的内容
        this.gcenter = gc;
        idg = new BigInteger(String.valueOf(System.currentTimeMillis())).pow(2);
        q3 =  new CreateBigPrime().getPrime(500);
        q4 =  new CreateBigPrime().getPrime(500);
        ng = q3.multiply(q4);
        gmfy = q3.subtract(BigInteger.ONE).multiply(q4.subtract(BigInteger.ONE));
        eg = new BigInteger("96907");
        dg = new GCD().getInverseEle(eg, gmfy); // 这里有小问题
    }

    /**
     * 获取 wg
     * @return
     */
    public BigInteger getWg(BigInteger idi, BigInteger rc){
        BigInteger yc = gcenter.getYc();
        BigInteger base = idg.multiply(rc).multiply(new Exponentiation().expMode(yc,rc.multiply(GroupCenter.MyHash(String.valueOf(idi))),ng)).multiply(idi);
        BigInteger wg = new Exponentiation().expMode(base, dg.negate(), ng);
        return wg;
    }

    public BigInteger[] getGroupManagerInfo(){
        BigInteger[] res = {ng, eg, idg};
        return res;
    }

    public BigInteger getIdg() {
        return idg;
    }

    public BigInteger getNg() {
        return ng;
    }

    public BigInteger getEg() {
        return eg;
    }

    /**
     * 验证签名
     * @param sign
     * @return
     */
    public boolean checkSign(Object[] sign){
        BigInteger pk = (BigInteger)sign[5];
        BigInteger dc = (BigInteger)sign[6];
        BigInteger c = gcenter.getCRTC();
        BigInteger ec = gcenter.getEc();
        BigInteger nc = gcenter.getNc();
        BigInteger right1 = new Exponentiation().expMode(pk,dc.multiply(ec),nc);
        boolean f1 = (pk.compareTo(right1)==0)?true:false;
        BigInteger yt =c.mod(pk);
        BigInteger z1p = 

        return true;


    }


}
