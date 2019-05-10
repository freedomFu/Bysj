package com.folm.bysj.testMath;

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

    public GroupMember(BigInteger x, BigInteger y, BigInteger pi) {
        // 暂时用时间戳代替userId
        userId = System.currentTimeMillis();
        this.x = x;
        this.y = y;
        this.pi = pi;
    }

    /**
     *
     * @param gs
     * @return
     */
    public BigInteger[] getGroupMemberSelfKey(GroupSinature gs){
        BigInteger[] gmsf = {x, pi, pi.pow(gs.getGroupPubKey().intValue())};
        return gmsf;
    }
}
