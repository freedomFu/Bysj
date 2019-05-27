package com.folm.improvePlan.model;

import com.folm.improvePlan.Utils.CreateBigPrime;
import com.folm.improvePlan.Utils.Exponentiation;
import com.folm.improvePlan.Utils.KnowledgeSinature;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 群成员类
 * @author folm
 */
public class GroupMember {
    private BigInteger k;
    private BigInteger idi;
    private GroupCenter gcenter;
    private BigInteger delta; //知识签名
    private int[] xy;// 坐标
    private ArrayList<Object[]> recordList;

    public GroupMember(GroupCenter gc, int[] xy, ArrayList<Object[]> recordList){
        // 传入群中心，然后可以在群成员中使用群中心公布的内容
        this.gcenter = gc;
        k = new CreateBigPrime().getPrime(100);
        BigInteger g = gc.getG();
        idi = new Exponentiation().expMode(g,k,g.subtract(BigInteger.ONE).multiply(k.subtract(BigInteger.ONE)));
        delta = new KnowledgeSinature().spk(idi, g);
        this.xy = xy;
        this.recordList= recordList;
    }

    public BigInteger getIdi() {
        return idi;
    }
}
