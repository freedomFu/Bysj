package com.folm.improvePlan.model;

import com.folm.improvePlan.Utils.CreateBigPrime;
import com.folm.improvePlan.Utils.Exponentiation;
import com.folm.improvePlan.Utils.GCD;
import com.folm.improvePlan.gui.TextPanel;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
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

    public GroupManager(GroupCenter gc, TextPanel tp){
        // 传入群中心，然后可以在群成员中使用群中心公布的内容
        tp.appendTextLn("========================= 群管理员生成过程 —— 开始 =========================");
        this.gcenter = gc;
        idg = new BigInteger(String.valueOf(System.currentTimeMillis())).pow(2);
        tp.appendTextLn("群管理员IDg："+idg);
        q3 =  new CreateBigPrime().getPrime(500);
        q4 =  new CreateBigPrime().getPrime(500);
        tp.appendTextLn("选取大素数1："+q3);
        tp.appendTextLn("选取大素数2："+q4);
        ng = q3.multiply(q4);
        tp.appendTextLn("计算ng："+ng);
        gmfy = q3.subtract(BigInteger.ONE).multiply(q4.subtract(BigInteger.ONE));
        eg = new BigInteger("96907");
        tp.appendTextLn("求得eg："+eg);
        dg = new GCD().getInverseEle(eg, gmfy);
        tp.appendTextLn("========================= 群管理员生成过程 —— 结束 =========================");
    }

    /**
     * 获取 wg
     * @return
     */
    public BigInteger[] getWg(BigInteger[] idi){
        Object[] getInfoFromlegalCenter = gcenter.getEleCheckNewMemberLegal(idi);
        BigInteger rc = (BigInteger) getInfoFromlegalCenter[1];
        BigInteger idis = new Exponentiation().expMode(idi[0],idi[1],ng);
        BigInteger hashMsg = GroupCenter.MyHash(String.valueOf(idis));
        // 获取 g 和 sc
//        BigInteger g = ((BigInteger[]) getInfoFromlegalCenter[2])[0];
//        BigInteger sc = ((BigInteger[]) getInfoFromlegalCenter[2])[1];
        BigInteger yc = gcenter.getYc();
        BigInteger base = idg.multiply(rc).multiply(new Exponentiation().expMode(yc,rc.multiply(hashMsg),ng)).multiply(idis).mod(ng);
        BigInteger[] wgArr = new Exponentiation().expModeArr(base, dg.negate(), ng);
        return wgArr;
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
        // 解析签名中的内容
        BigInteger pk = (BigInteger) sign[5];
        BigInteger[] pkdc = (BigInteger[]) sign[6];
        BigInteger u = (BigInteger)sign[1];
//        System.out.println("验证u:"+u);
        BigInteger r1 = (BigInteger)sign[2];
        Object[] r2 = (Object[]) sign[3];
        BigInteger r2left = (BigInteger)r2[0];
        BigInteger r2rightbase = (BigInteger) r2[1];
        BigInteger r2rightexpo1 = (BigInteger) r2[2]; // u
        BigInteger r2rightexpo2 = (BigInteger) r2[3]; //dg的相反数
        BigInteger r2right = r2rightexpo1.multiply(r2rightexpo2.negate().multiply(eg).mod(gmfy)).negate();

        BigInteger[] r3 = (BigInteger[])((Object[])sign[4])[0];
        BigInteger beta1 = (BigInteger)((Object[])sign[4])[1];
        BigInteger r3base = r3[0];
        BigInteger r3expo = r3[1];
        String msg = (String)sign[0];
        // 获取公开的信息
        BigInteger c = gcenter.getCRTC();
        BigInteger g = gcenter.getG();
        BigInteger ec = gcenter.getEc();
        BigInteger nc = gcenter.getNc();
        BigInteger right1 = new Exponentiation().expMode(pk,pkdc[1].multiply(ec),nc);
        boolean f1 = (pk.compareTo(right1)==0)?true:false;
//        System.out.println("第一个验证："+f1);

        // 完成第二个验证
        BigInteger yt =c.mod(pk);
        BigInteger z1p = new Exponentiation().expMode(r3base, r3expo.multiply(yt), nc);
        System.out.println("z1p："+z1p);
        BigInteger z2pf = new Exponentiation().expMode(idg,u,ng);
        BigInteger z2ps = new Exponentiation().expMode(g,r1,ng);
        BigInteger z2pt = new Exponentiation().expMode(r2left,eg,ng);
        BigInteger z2pfo = new Exponentiation().expMode(r2rightbase,r2right,ng);
        BigInteger z2p;
        if(r2right.compareTo(u.negate()) == 0){
            z2p = (new Exponentiation().expMode(r2left,eg,ng)).multiply(new Exponentiation().expMode(g,beta1,ng)).mod(ng);
        }else{
            z2p = z2pf.multiply(z2ps).multiply(z2pt).multiply(z2pfo).mod(ng);
        }

//        System.out.println("z2p:"+z2p);
        BigInteger up = GroupCenter.MyHash(z1p,z2p,msg);

        if(u.compareTo(up)==0){
            return true;
        }
        return false;
    }

    /**
     * 群管理员 获取成员信息
     * @return
     */
    public ArrayList<Object[]> getMemberList(){
        ArrayList<Object[]> memberList = new ArrayList<>();
        List<GroupMember> gmlist = gcenter.getMemberRecordList();
        BigInteger g = gcenter.getG();
        for(int i=0;i<gmlist.size();i++){
            GroupMember gm = gmlist.get(i);
            BigInteger[] idi = gm.getIdi();
            BigInteger[] rcSc = gcenter.getrcSc(idi);
            BigInteger sc = rcSc[1];
            BigInteger[] wg = getWg(idi);
            BigInteger[] gsc = new Exponentiation().expMode(g,sc);

            Object[] res = {idi, wg, gsc};
            memberList.add(res);
        }
        return memberList;
    }

    /**
     * 揭露用户
     * @param sign 指该签名
     * @return
     */
    public BigInteger showSignMemberidi(Object[] sign){
        ArrayList<Object[]> memberList = this.getMemberList();
        int size = memberList.size();
        BigInteger u = (BigInteger)sign[1];
        BigInteger r1 = (BigInteger)sign[2];
        BigInteger g = gcenter.getG();
        BigInteger fai = (BigInteger.ONE.divide(u)).mod(gmfy);
        BigInteger[] res = (BigInteger[])sign[7];
        System.out.println(Arrays.toString(res));
        BigInteger lamda = new Exponentiation().expMode(idg,u,ng).multiply(new Exponentiation().expMode(g,r1.mod(ng),ng)).mod(ng);
        BigInteger r = new Exponentiation().expMode(res[0],res[1],ng);
        for(int i=0;i<size;i++){
            Object[] member = memberList.get(i);
            BigInteger[] gsc = (BigInteger[]) member[2];
            BigInteger[] wg = (BigInteger[]) member[1];
            BigInteger wgbase = wg[0];
            BigInteger wgexp = wg[1];
            BigInteger[] idInArray = (BigInteger[]) member[0];
            BigInteger idres = new Exponentiation().expMode(idInArray[0],idInArray[1],ng);

            BigInteger upbase = new Exponentiation().expMode(g,r1,ng).divide((lamda.multiply(new Exponentiation().expMode(g,u.multiply(gsc[1]),ng))));
            BigInteger up = new Exponentiation().expMode(upbase,fai,ng);
            BigInteger down = new Exponentiation().expMode(wgbase,wgexp.multiply(eg).mod(gmfy),ng);
            BigInteger idi = up.divide(down).mod(ng);


            if(r.compareTo(idres) == 0){
                return idres;
            }
        }

        return r;
    }
}