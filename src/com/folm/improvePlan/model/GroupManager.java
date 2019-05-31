package com.folm.improvePlan.model;

import com.folm.improvePlan.Utils.CreateBigPrime;
import com.folm.improvePlan.Utils.Exponentiation;
import com.folm.improvePlan.Utils.GCD;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 群管理员
 * 没必要建立内部类，可以使用列表来建立联系
 * @author folm
 */
public class GroupManager {
    // 群管理员身份
    private BigInteger idg;
    // 随机选择大素数 以及乘积
    private BigInteger q3;
    private BigInteger q4;
    private BigInteger ng;
    private BigInteger gmfy;
    // 两个大整数
    private BigInteger eg;
    private BigInteger dg;
    private GroupCenter gcenter;

    public GroupManager(GroupCenter gc){
        // 传入群中心，然后可以在群成员中使用群中心公布的内容
        this.gcenter = gc;
        idg = new BigInteger(String.valueOf(System.currentTimeMillis())).pow(2);
        q3 =  new CreateBigPrime().getPrime(500);
        q4 =  new CreateBigPrime().getPrime(500);
        ng = q3.multiply(q4);
        gmfy = q3.subtract(BigInteger.ONE).multiply(q4.subtract(BigInteger.ONE));
        eg = new BigInteger("96907");
        dg = new GCD().getInverseEle(eg, gmfy); // 这里有小问题
    }

    /**
     * 获取 wg
     * @return
     */
    public BigInteger getWg(BigInteger idi, BigInteger rc){
        BigInteger yc = gcenter.getYc();
        BigInteger base = idg.multiply(rc).multiply(new Exponentiation().expMode(yc,rc.multiply(GroupCenter.MyHash(String.valueOf(idi))),ng)).multiply(idi);
        BigInteger wg = new Exponentiation().expMode(base, dg.negate(), ng);
        return wg;
    }

    public BigInteger[] getGroupManagerInfo(){
        BigInteger[] res = {ng, eg, idg};
        return res;
    }

    public BigInteger getIdg() {
        return idg;
    }

    public BigInteger getNg() {
        return ng;
    }

    public BigInteger getEg() {
        return eg;
    }

    /**
     * 验证签名
     * @param sign
     * @return
     */
    public boolean checkSign(Object[] sign){
        BigInteger pk = (BigInteger)sign[5];
        BigInteger dc = (BigInteger)sign[6];
        BigInteger u = (BigInteger)sign[1];
        System.out.println("验证u"+u);
        BigInteger r1 = (BigInteger)sign[2];
        BigInteger r2 = (BigInteger)sign[3];
        BigInteger r3 = (BigInteger)sign[4];
        String msg = (String)sign[0];
        BigInteger c = gcenter.getCRTC();
        BigInteger g = gcenter.getG();
        BigInteger ec = gcenter.getEc();
        BigInteger nc = gcenter.getNc();
        BigInteger right1 = new Exponentiation().expMode(pk,dc.multiply(ec),nc);
        boolean f1 = (pk.compareTo(right1)==0)?true:false;
        BigInteger yt =c.mod(pk);
        BigInteger z1p = new Exponentiation().expMode(r3, yt, nc);
        System.out.println("z1p"+z1p);
        BigInteger z2pf = new Exponentiation().expMode(idg,u,ng);
        BigInteger z2ps = new Exponentiation().expMode(g,r1,ng);
        BigInteger z2pt = new Exponentiation().expMode(r2,eg,ng);
        BigInteger z2p = z2pf.multiply(z2ps).multiply(z2pt).mod(ng);
        String mix = z1p+msg+z2p;
        BigInteger up = GroupCenter.MyHash(mix);
        System.out.println("验证up"+up);

        return true;
    }

    /**
     * 群管理员 获取成员信息
     * @return
     */
    private ArrayList<BigInteger[]> getMemberList(){
        ArrayList<BigInteger[]> memberList = new ArrayList<>();
        List<GroupMember> gmlist = gcenter.getMemberRecordList();
        BigInteger g = gcenter.getG();
        for(int i=0;i<gmlist.size();i++){
            GroupMember gm = gmlist.get(i);
            BigInteger idi = gm.getIdi();
            BigInteger[] rcSc = gcenter.getEleCheckNewMemberLegal(idi);
            BigInteger rc = rcSc[1];
            BigInteger sc = rcSc[3];
            BigInteger wg = getWg(idi, rc);

            BigInteger[] res = {idi, wg, g, sc};
            memberList.add(res);
        }
        return memberList;
    }

    /**
     * 揭露用户
     * @param sign
     * @return
     */
    public BigInteger showSignMemberidi(Object[] sign){
        ArrayList<BigInteger[]> memberList = this.getMemberList();
        int size = memberList.size();
        String msg = (String)sign[0];
        BigInteger u = (BigInteger)sign[1];
        BigInteger r1 = (BigInteger)sign[2];
        BigInteger r2 = (BigInteger)sign[3];
        BigInteger r3 = (BigInteger)sign[4];
        BigInteger pk = (BigInteger)sign[5];
        BigInteger dc = (BigInteger)sign[6];
        BigInteger g = gcenter.getG();
        BigInteger fai = (BigInteger.ONE.divide(u.mod(gmfy))).mod(gmfy);
        BigInteger lamda = new Exponentiation().expMode(idg,u,ng).multiply(new Exponentiation().expMode(g,r1,ng)).multiply(ng);

        for(int i=0;i<size;i++){
            BigInteger[] member = memberList.get(i);
            BigInteger sc = member[3];
            g = member[2];
            BigInteger wg = member[1];
            BigInteger idInArray = member[0];

            BigInteger upbase = new Exponentiation().expMode(g,r1,ng).divide((lamda.multiply(new Exponentiation().expMode(g,u.multiply(sc),ng))));
            BigInteger up = new Exponentiation().expMode(upbase,fai,ng);
            BigInteger down = new Exponentiation().expMode(wg,eg,ng);
            BigInteger idi = up.divide(down).mod(ng);

            if(idi.compareTo(idInArray) == 0){
                return idi;
            }
        }
        return BigInteger.ZERO;
    }
}