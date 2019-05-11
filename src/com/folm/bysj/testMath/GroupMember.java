package com.folm.bysj.testMath;

import com.folm.bysj.math.Exponentiation;

import java.math.BigInteger;

/**
 * 群成员类
 * @author folm
 */
public class GroupMember {
    // 群成员Ui 的私钥和公钥  x为私钥   y为公钥
    private BigInteger x;
    private BigInteger y;
    private long userId;
    private BigInteger pi;
    private BigInteger pipow;

    public GroupMember(BigInteger x, BigInteger y, BigInteger pi, BigInteger pipow) {
        // 暂时用时间戳代替userId
        userId = System.currentTimeMillis();
        this.x = x;
        this.y = y;
        this.pi = pi;
        this.pipow = pipow;
    }

    protected long getUserId() {
        return userId;
    }

    protected BigInteger getY() {
        return y;
    }

    protected BigInteger getPi() {
        return pi;
    }

    @Override
    public String toString() {
        return "x:"+x+",\ny:"+y+",\npipow:"+pipow;
    }

    /**
     * @return
     */
    public BigInteger[] getGroupMemberSelfKey(){
        BigInteger[] gmsf = {x, pi, pipow};
        return gmsf;
    }

    /**
     * 成员对消息进行签名
     * @param msg
     * @return
     */
    public BigInteger[] sinature(GroupSinature gs,String msg){
        BigInteger m = gs.MyHash(msg);
        BigInteger ni = gs.getnByUserId(userId);
        BigInteger si = new Exponentiation().expMode(m, x, ni);
        BigInteger[] res = {m, si, pipow};
        return res;
    }
}
