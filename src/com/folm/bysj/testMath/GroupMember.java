package com.folm.bysj.testMath;

import java.math.BigInteger;

public class GroupMember {
    // 群成员Ui 的私钥和公钥  x为私钥   y为公钥
    private BigInteger x;
    private BigInteger y;
    public int userId;

    public GroupMember(BigInteger x, BigInteger y) {
        this.x = x;
        this.y = y;
    }
}
