package com.folm.bysj.model;

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

    public long getUserId() {
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
     * 得到si
     * @param gs
     * @param msg
     * @return
     */
    public BigInteger sinatureSi(GroupSinature gs,String msg){
        BigInteger m = gs.MyHash(msg);
        BigInteger ni = this.getNi(gs);
        BigInteger si = new Exponentiation().expMode(m, x, ni);
        return si;
    }

    public BigInteger getNi(GroupSinature gs){
        BigInteger ni = gs.getnByUserId(userId);
        return ni;
    }

    /**
     * 进行签名 并返回数组
     * @param gs
     * @param msg
     * @return
     */
    public Object[] sign(GroupSinature gs, String msg){
        BigInteger si= sinatureSi(gs, msg);
        Object[] res = {msg, si, pipow};

        return res;
    }
}
