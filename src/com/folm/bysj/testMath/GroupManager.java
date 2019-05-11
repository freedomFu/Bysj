package com.folm.bysj.testMath;

import com.folm.bysj.math.Exponentiation;

import java.math.BigInteger;

/**
 * 群管理员类
 * @author folm
 */
public class GroupManager {

    /**
     * 验证签名
     * @param gs 群中心对象
     * @param gm 群成员对象
     * @param msg 信息
     * @return
     */
    public boolean isSignCorrect(GroupSinature gs, GroupMember gm, String msg){
        BigInteger c = gs.crtGetc();
        Object[] res = gm.sign(gs,msg);
        BigInteger pipow = (BigInteger) res[2];
        BigInteger si = (BigInteger) res[1];

        BigInteger n = gs.getN();
        BigInteger pi = pipow.mod(n);
        BigInteger yi = c.mod(pi);
        BigInteger ni = gm.getNi(gs);

        BigInteger check_hm = new Exponentiation().expMode(si, yi, ni);
        BigInteger old_hm = gs.MyHash(msg);

        if(check_hm.compareTo(old_hm) == 0){
            return true;
        }else{
            return false;
        }
    }

    public Long openSign(GroupSinature gs, GroupMember gm, String msg){
        // 通过打开签名得到 用户信息
        // 得到用户签名
        Object[] sign = gm.sign(gs, msg);
        BigInteger si = (BigInteger)sign[1];
        BigInteger pid = (BigInteger)sign[2];
        BigInteger n = gs.getN();
        BigInteger c = gs.crtGetc();
        BigInteger pi = pid.mod(n);

        BigInteger yi = c.mod(pi);
        BigInteger ni = gm.getNi(gs);

        BigInteger check_hm = new Exponentiation().expMode(si, yi, ni);
        BigInteger old_hm = gs.MyHash(msg);
        if(check_hm.compareTo(old_hm)==0){
            long userId = gs.getUserIdByYiAndNi(yi, ni);
            return userId;
        }else{
            return null;
        }
    }
}
