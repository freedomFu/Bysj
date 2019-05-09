package com.folm.bysj.math;

import java.math.BigInteger;

/**
 * 求最大公约数
 * @author folm
 */
public class Gcd {
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
}
