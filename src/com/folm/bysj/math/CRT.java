package com.folm.bysj.math;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class CRT {
    /**
     * 获取最后的满足这个同余线性方程组的值
     * @param b 余数list
     * @param m 模数list
     * @return
     */
    public BigInteger getRes(List<BigInteger> b, List<BigInteger> m){
        int n = (b.size()==m.size())?b.size():0;

        BigInteger M_All = BigInteger.ONE;
        BigInteger res = BigInteger.ZERO;
        List<BigInteger> M = new ArrayList<>();
        List<BigInteger> Mi = new ArrayList<>();

        for (int i=0;i<n;i++){
            M_All = M_All.multiply(m.get(i));
        }
        // 获取M list
        for (int i=0;i<n;i++){
            BigInteger M_tmp = M_All.divide(m.get(i));
            M.add(M_tmp);
//            Mi.add(getMi(M_tmp,m.get(i)));
            // 添加 Mi list  通过 扩展欧几里得算法得到最终结果
            Mi.add(new Gcd().getInverseEle(M_tmp,m.get(i)));
        }
        for (int i=0;i<n;i++){
            res = res.add(M.get(i).multiply(Mi.get(i)).multiply(b.get(i)));
        }
        res = res.mod(M_All);

        return res;
    }
}