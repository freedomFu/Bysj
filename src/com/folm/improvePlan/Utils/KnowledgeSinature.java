package com.folm.improvePlan.Utils;

import java.math.BigInteger;

/**
 * 知识签名
 */
public class KnowledgeSinature {

    public BigInteger spk(BigInteger id, BigInteger g){
        return id.pow(2).subtract(BigInteger.TWO).multiply(g.pow(3).subtract(BigInteger.TEN));
    }

}
