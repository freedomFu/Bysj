package com.folm.improvePlan.model;

import com.folm.improvePlan.Utils.CreateBigPrime;
import com.folm.improvePlan.Utils.GCD;

import java.math.BigInteger;

/**
 * 群管理员
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

    public GroupManager(){
        idg = new BigInteger(String.valueOf(System.currentTimeMillis())).pow(2);
        q3 =  new CreateBigPrime().getPrime(900);
        q4 =  new CreateBigPrime().getPrime(900);
        ng = q3.mod(q4);
        gmfy = q3.subtract(BigInteger.ONE).multiply(q4.subtract(BigInteger.ONE));
        eg = new BigInteger("96907");
        dg = new GCD().getInverseEle(eg, gmfy);
    }

    public BigInteger[] getGroupManagerInfo(){
        BigInteger[] res = {ng, eg, idg};
        return res;
    }

    public static void main(String[] args) {
        GroupManager gm = new GroupManager();
    }

}
