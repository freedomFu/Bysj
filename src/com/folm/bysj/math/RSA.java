package com.folm.bysj.math;

import java.math.BigInteger;

/**
 * RSA 加密、解密、测试正确性
 * @author folm
 */
public class RSA {
    /**
     * 产生公钥、私钥
     * @param p 大素数 p
     * @param q 大素数 q
     * @return 返回公钥、私钥
     */
    public BigInteger[][] genkey(BigInteger p, BigInteger q, BigInteger e){
        BigInteger n = p.multiply(q);
        BigInteger fy = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        // 得到解密需要的d
        BigInteger a = e;
        BigInteger b = fy;
        BigInteger[] rxy = new Gcd().extGcd(a,b);
        BigInteger r = rxy[0];
        BigInteger x = rxy[1];
        BigInteger y = rxy[2];
//        System.out.println("转换之前的x:"+x);

        if(x.compareTo(BigInteger.ZERO) == -1){
            x = x.add(fy);
        }

        BigInteger d = x;
//        System.out.println("输出x"+x);
        // 返回 公钥 ， 私钥
        // 公钥用于产生 密文，私钥用于恢复明文
        return new BigInteger[][]{{n, e}, {n, d}};
    }

    /**
     * 加密
     * @param m 被加密的信息转换成为大整数m
     * @param pubkey 公钥
     * @return 返回加密之后的数字
     */
    public BigInteger encrypt(BigInteger m, BigInteger[] pubkey){
        BigInteger n = pubkey[0];
        BigInteger e = pubkey[1];

        BigInteger c = new Exponentiation().expMode(m, e, n);
        return c;
    }

    /**
     * 解密
     * @param c
     * @param selfkey 私钥
     * @return 返回解密之后的数字
     */
    public BigInteger decrypt(BigInteger c, BigInteger[] selfkey){
        BigInteger n = selfkey[0];
        BigInteger d = selfkey[1];

        BigInteger m = new Exponentiation().expMode(c, d, n);
        return m;
    }



}
