package com.folm.improvePlan.Utils;

import java.math.BigInteger;

/**
 * 求最大公约数
 * 欧几里得算法 以及 拓展欧几里得算法
 * @author folm
 */
public class GCD {

    /**
     * 辗转相除法求最大公约数
     * @param a
     * @param b
     * @return 返回a和b最大公约数
     */
    public BigInteger gcd(BigInteger a, BigInteger b){
        if(b.equals(BigInteger.ZERO)){
            return a;
        }else{
            return gcd(b, a.mod(b));
        }
    }

    /**
     * 拓展欧几里得算法
     * @param a
     * @param b
     * @return 返回最大公约数以及参数
     */
    public BigInteger[] extGcd(BigInteger a, BigInteger b){
        if(b.equals(BigInteger.ZERO)){
            BigInteger x1 = BigInteger.ONE;
            BigInteger y1 = BigInteger.ZERO;
            BigInteger x = x1;
            BigInteger y = y1;
            BigInteger r = a;
            BigInteger[] result = {r, x, y};

            return result;
        }else{
            BigInteger[] temp = extGcd(b, a.mod(b));
            BigInteger r = temp[0];
            BigInteger x1 = temp[1];
            BigInteger y1 = temp[2];

            BigInteger x = y1;
            BigInteger y = x1.subtract(a.divide(b).multiply(y1));
            BigInteger[] result = {r, x, y};
            return result;
        }
    }

    /**
     * 获取逆元
     * @param a
     * @param b
     * @return
     */
    public BigInteger getInverseEle(BigInteger a, BigInteger b){
        BigInteger x = extGcd(a, b)[1];
        if(x.compareTo(BigInteger.ZERO) == -1){
            x = x.add(b);
        }
        return x;
    }

}
