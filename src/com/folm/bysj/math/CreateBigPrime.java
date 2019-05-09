package com.folm.bysj.math;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * 产生大素数的算法
 * @author folm
 */
public class CreateBigPrime {

    private static Random ran = null;

    static{
        ran = new SecureRandom();
    }

    /**
     * 获取大素数
     * @param length
     * @return
     */
    public BigInteger getPrime(int length){
        BigInteger p;
        int times = getTimes(length);

        do {
            p = BigInteger.probablePrime(length,ran);
            if (p.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) continue;
            if (p.mod(BigInteger.valueOf(3)).equals(BigInteger.ZERO)) continue;
            if (p.mod(BigInteger.valueOf(5)).equals(BigInteger.ZERO)) continue;
            if (p.mod(BigInteger.valueOf(7)).equals(BigInteger.ZERO)) continue;
        } while (!millerRabin(p,times));

        return p;
    }

    /**
     * 根据长度获取判断次数
     * @param length
     * @return
     */
    private static int getTimes(int length){
        int times = 0;
        if(length<100){
            times = 50;
        }else if(length <256){
            times = 27;
        }else if(length < 512){
            times = 15;
        }else if(length < 768){
            times = 8;
        }else if(length < 1024){
            times = 4;
        }else{
            times = 2;
        }

        return times;
    }

    /**
     * 判断的算法  多次使用 正确概率增大
     * @param n
     * @param times
     * @return
     */
    private static boolean millerRabin(BigInteger n,int times) {
        for (int i = 0; i < times; i++) {
            BigInteger a;
            do {
                a = new BigInteger(n.bitLength(), ran);
            } while (a.equals(BigInteger.ZERO));
            if (!millerRabinPass(a, n)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 应用 Miller-Rabin算法判断是不是素数
     * @param a
     * @param n
     * @return
     */
    private static boolean millerRabinPass(BigInteger a, BigInteger n) {
        BigInteger nMinusOne = n.subtract(BigInteger.ONE);
        BigInteger d = nMinusOne;
        int s = d.getLowestSetBit();
        d = d.shiftRight(s);

        BigInteger aToPower = a.modPow(d, n);
        if (aToPower.equals(BigInteger.ONE)) return true;

        // was the most well-documented
        for (int i = 0; i < s-1; i++) {
            if (aToPower.equals(nMinusOne)) return true;
            aToPower = aToPower.multiply(aToPower).mod(n);
        }
        if (aToPower.equals(nMinusOne)) return true;
        return false;
    }
}
