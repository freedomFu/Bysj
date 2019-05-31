package com.folm.improvePlan.Utils;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 主要用于计算超大整数的超大次幂 然后 对超大的整数取模
 * "蒙哥马利算法"
 * 模幂乘算法
 * @author folm
 */

public class Exponentiation {
    /**
     * 超大整数超大次幂然后对超大的整数取模
     * (base ^ exponent) mod n
     * @param base 底数
     * @param exponent 幂
     * @param n 模
     * @return 返回大整数
     */
    public BigInteger expMode(BigInteger base, BigInteger exponent, BigInteger n){
        // 这里把指数转换成了二进制，并进行了反转
        char[] binaryArray = new StringBuilder(exponent.toString(2)).reverse().toString().toCharArray();
        int r = binaryArray.length;
        // 创建一个存储基数的list
        List<BigInteger> baseArray = new ArrayList<>();
        // 获取基数 并存储到 list 当中
        BigInteger preBase = base;
        baseArray.add(preBase);
        // 循环计算 每次循环之后的基数
        for(int i=0;i<r-1;i++){
            BigInteger nextBase = preBase.multiply(preBase).mod(n);
            baseArray.add(nextBase);
            preBase = nextBase; // 更新前一个基数的值
        }

        BigInteger a_w_b = this.multi(baseArray.toArray(new BigInteger[baseArray.size()]),binaryArray,n);
        // 最后的取模操作与正常的取模操作无异
        return a_w_b.mod(n);
    }

    public BigInteger[] expMode(BigInteger base, BigInteger exponent){
        BigInteger[] res = {base, exponent};
        return res;
    }

    /**
     *
     * @param array 基数数组
     * @param bin_array 二进制数组 来决定基数数组的值 是本身还是1
     * @param n 模数
     * @return
     */
    private BigInteger multi(BigInteger[] array, char[] bin_array, BigInteger n){
        BigInteger result = BigInteger.ONE;
        for(int index=0;index<array.length;index++){
            BigInteger a = array[index];
            // 如果二进制为0 就相当于乘以1，也就是不执行
            if(bin_array[index] == '0'){
                continue;
            }
            // 进行乘法以及取模运算
            result = result.multiply(a);
            result = result.mod(n);
        }
        return result;
    }
}
