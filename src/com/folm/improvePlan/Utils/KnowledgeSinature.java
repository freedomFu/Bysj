package com.folm.improvePlan.Utils;

import java.math.BigInteger;

/**
 * 知识签名
 */
public class KnowledgeSinature {

    public BigInteger spk(BigInteger[] id, BigInteger g){
        BigInteger is = new Exponentiation().expMode(id[0],id[1],id[0].multiply(id[1]));
        return is.pow(2).subtract(BigInteger.TWO).multiply(g.pow(3).subtract(BigInteger.TEN));
    }

}
