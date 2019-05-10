package com.folm.bysj.testMath;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.folm.bysj.math.*;

public class Test {

    public static void test1(){
        Gcd g = new Gcd();
        System.out.println(g.gcd(new BigInteger("321312321312"),new BigInteger("12332131233333333212")));

        System.out.println("扩展欧几里得算法");
        System.out.println(Arrays.toString(g.extGcd(new BigInteger("6666666667"), new BigInteger("11"))));

        Exponentiation ex = new Exponentiation();
        System.out.println("蒙哥马利算法");
        System.out.println(ex.expMode(new BigInteger("1000"),new BigInteger("100"),new BigInteger("988")));

        BigInteger i = new BigInteger("10000000000000");
        System.out.println(i.toString(2));
    }

    public static void test2(){
        // 公钥私钥中用到的两个大质数p,q
        BigInteger p = new CreateBigPrime().getPrime(1024);
        BigInteger q = new CreateBigPrime().getPrime(1024);

        System.out.println(p);
        System.out.println(q);

        RSA rsa = new RSA() ;

        // 生成公钥私钥
        // pubkey, selfkey = gen_key(p, q)
        BigInteger[][] keys = rsa.genkey(p, q, new BigInteger("65537"));
        BigInteger[] pubkey  = keys[0] ;
        BigInteger[] selfkey = keys[1] ;

        // 需要被加密的信息转化成数字，长度小于秘钥n的长度，如果信息长度大于n的长度，那么分段进行加密，分段解密即可。
        BigInteger m = new BigInteger("1356205320457610288745198967657644166379972189839804389074591563666634066646564410685955217825048626066190866536592405966964024022236587593447122392540038493893121248948780525117822889230574978651418075403357439692743398250207060920929117606033490559159560987768768324823011579283223392964454439904542675637683985296529882973798752471233683249209762843835985174607047556306705224118165162905676610067022517682197138138621344578050034245933990790845007906416093198845798901781830868021761765904777531676765131379495584915533823288125255520904108500256867069512326595285549579378834222350197662163243932424184772115345") ;
        System.out.println("被加密信息：" + m);
        // 信息加密
        BigInteger c = rsa.encrypt(m, pubkey) ;
        System.out.println("密文：" + c);
        // 信息解密
        BigInteger d = rsa.decrypt(c, selfkey) ;
        System.out.println("被解密后信息：" + d);
    }

    public static void test3(){
        GroupSinature gs = new GroupSinature();
    }

    public static void test4(){
        CRT c = new CRT();
        List<BigInteger> mode = new ArrayList<>();
        mode.add(new BigInteger("3"));
        mode.add(new BigInteger("5"));
        mode.add(new BigInteger("7"));

        List<BigInteger> remain = new ArrayList<>();
        remain.add(new BigInteger("2"));
        remain.add(new BigInteger("3"));
        remain.add(new BigInteger("2"));
        System.out.println(c.getRes(remain, mode));
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
    }

}
