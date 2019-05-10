package com.folm.bysj.testMath;

import com.folm.bysj.math.CreateBigPrime;
import com.folm.bysj.math.Exponentiation;
import com.folm.bysj.math.RSA;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class GroupSinature {

    private List<GroupMember> memberList = new ArrayList<>();
    // 生成群中心的公钥和私钥
    private BigInteger p = new CreateBigPrime().getPrime(1024);
    private BigInteger q = new CreateBigPrime().getPrime(1024);
    private BigInteger n = p.multiply(q);

    private BigInteger e = new BigInteger("65537");
    RSA rsa = new RSA();

    /**
     * 生成群中心的私钥 公钥
     * @return
     */
    public BigInteger[] getGroupKey(){

        BigInteger[][] key = rsa.genkey(p, q, e);
        BigInteger[] res = {key[0][1],key[1][1]};
        return res;
    }

    private BigInteger getFy(BigInteger p, BigInteger q){
        return p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    }

    public Object[] createKey(){
        // 群中心生成两个大素数，并生成群成员
        BigInteger r = new CreateBigPrime().getPrime(1024);
        BigInteger q = new CreateBigPrime().getPrime(1024);
        BigInteger fy = getFy(r, q);

        BigInteger y = new CreateBigPrime().getPrime(512);
        BigInteger[][] key = rsa.genkey(r, q, y);
        // 求x
        BigInteger x = key[1][1];
        boolean flag = (x.multiply(y).mod(fy).compareTo(BigInteger.ONE) == 0)?true:false;
        // 返回该群成员的公钥 y 和 私钥 x
        Object[] res = {y, x, flag};
        return res;
    }

    public void addMember(){
        while(true){
            if((boolean)this.createKey()[2]){
                GroupMember gm = new GroupMember((BigInteger) this.createKey()[1], (BigInteger) this.createKey()[0]);
                memberList.add(gm);
                break;
            }else{
                continue;
            }
        }
    }
    // 如果验证成功   群成员就可以相信 这个信息是 由群中心发送过来的
    // 这个内容发送到 用户那里验证  如果成立 这个 信息就会作为用户私钥保存下来
    public void isValid(){
        BigInteger p = new CreateBigPrime().getPrime(513);

        BigInteger left = p.mod(n);
        BigInteger e = getGroupKey()[0];
        BigInteger d = getGroupKey()[1];

        BigInteger right = new Exponentiation().expMode(p, d.multiply(e), n);
    }






}
