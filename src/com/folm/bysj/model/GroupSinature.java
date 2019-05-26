package com.folm.bysj.model;

import com.folm.bysj.gui.TextPanel;
import com.folm.bysj.math.CRT;
import com.folm.bysj.math.CreateBigPrime;
import com.folm.bysj.math.Exponentiation;
import com.folm.bysj.math.RSA;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 群中心类
 * @author folm
 */
public class GroupSinature {
    private TextPanel tp;
    private List<GroupMember> memberList;
    private Map<Long, BigInteger> manageInfoMap;
    private Map<Long, BigInteger> userInfoMap;
    // 两个大素数p、q以及它们的乘积，还有选择的大整数 e
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger e;
    private RSA rsa;

    public GroupSinature(TextPanel tp){
        this.tp = tp;
        memberList = new ArrayList<>(); //成员list
        manageInfoMap = new HashMap<>(); // 管理员获取到的成员信息
        userInfoMap = new HashMap<>(); // 成员信息

        // 两个大素数p、q以及它们的乘积，还有选择的大整数 e
        p = new CreateBigPrime().getPrime(1024); 
        q = new CreateBigPrime().getPrime(1024);
        n = p.multiply(q);
        e = new BigInteger("65537");
        rsa = new RSA();
        tp.appendTextLn("已经成功进入群中心");
        tp.appendTextLn("========================= 群中心生成过程 —— 开始 =========================");
        tp.appendTextLn("产生两个大素数p、q！");
        tp.appendTextLn("p："+p);
        tp.appendTextLn("q："+q);
        tp.appendTextLn("计算 n=pxq："+n);
        tp.appendTextLn("选择 e："+e);

        tp.appendTextLn("计算群中心的公钥和私钥：");
        tp.appendTextLn("群中心的公钥："+this.getGroupKey()[0]);
        tp.appendTextLn("群中心的私钥："+this.getGroupKey()[1]);
        tp.appendTextLn("========================= 群中心生成过程 —— 结束 =========================");
    }

    protected BigInteger getN() {
        return n;
    }

    public List<GroupMember> getMemberList() {
        return memberList;
    }

    /**
     * 生成群中心的私钥 公钥
     * @return
     */
    private BigInteger[] getGroupKey(){
        BigInteger[][] key = rsa.genkey(p, q, e);
        BigInteger[] res = {key[0][1],key[1][1]};
        return res;
    }

    /**
     * 获取欧拉函数值
     * @param p
     * @param q
     * @return
     */
    private BigInteger getFy(BigInteger p, BigInteger q){
        return p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    }

    /**
     *
     * @return
     */
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
        Object[] res = {y, x, flag, r.multiply(q)};
        return res;
    }

    /**
     *
     */
    public void addMember(){
        while(true){
            Object[] res = this.createKey();
            if((boolean)res[2]){
                tp.appendTextLn("");
                tp.appendTextLn("========================= 添加中心群成员 —— 开始 =========================");
                BigInteger[] pieArray = getValidP();
                BigInteger pi = pieArray[0];
                BigInteger pie = pieArray[1];
                GroupMember gm = new GroupMember((BigInteger)res[1], (BigInteger)res[0], pi, pie);
                memberList.add(gm);
                long userId = gm.getUserId();
                addGroupManger((BigInteger)res[0], userId);
                userInfoMap.put(userId, (BigInteger)res[3]);
                tp.appendTextLn("群成员公钥："+res[0]);
                tp.appendTextLn("群成员私钥："+res[1]);
                tp.appendTextLn("选择p："+pi);
                tp.appendTextLn("添加群成员 userId："+ userId +" 成功");
                tp.appendTextLn("========================= 添加中心群成员 —— 结束 =========================");
                break;
            }else{
                continue;
            }
        }
    }

    /**
     * 获取用户id
     * @param yi
     * @param ni
     * @return
     */
    public Long getUserIdByYiAndNi(BigInteger yi,BigInteger ni){
        Set<Long> res = new HashSet<>();
        // yi
        Set<Long> ySet = getKey(manageInfoMap, yi);
        // ni
        Set<Long> nSet = getKey(userInfoMap, ni);

        res.addAll(ySet);
        res.retainAll(nSet);

        if(res.size() == 1){
            return (Long)res.toArray()[0];
        }else{
            return null;
        }

    }

    public static Set<Long> getKey(Map<Long,BigInteger> map, BigInteger v) {
        Iterator<Long> it = map.keySet().iterator();
        Set<Long> res = new HashSet<>();

        while(it.hasNext()){
            long key = it.next();
            if(map.get(key).equals(v)){
                res.add(key);
            }
        }
        return res;
    }

    /**
     * 根据用户id获取n值
     * @param userId
     * @return
     */
    public BigInteger getnByUserId(long userId){
        if(userInfoMap.containsKey(userId)){
            return userInfoMap.get(userId);
        }
        return null;
    }

    // 如果验证成功   群成员就可以相信 这个信息是 由群中心发送过来的
    // 这个内容发送到 用户那里验证  如果成立 这个 信息就会作为用户私钥保存下来
    /**
     * 获取合法的p
     * @return
     */
    private BigInteger[] getValidP(){
        BigInteger p;
        BigInteger[] res = new BigInteger[2];
        BigInteger pie;
        while(true){
             p = new CreateBigPrime().getPrime(513);

            BigInteger left = p.mod(n);
            BigInteger e = getGroupKey()[0];
            BigInteger d = getGroupKey()[1];

            BigInteger right = new Exponentiation().expMode(p, d.multiply(e), n);
            pie = right;

            if(left.compareTo(right)==0){
                res[0] = p;
                res[1] = pie;
                break;
            }else{
                continue;
            }
        }
        return res;
    }
    /**
     * 获取群公钥，对于整个系统的所用用户开放，比如群成员、验证者等
     * @return
     */
    public BigInteger[] getGroupPubKey(){
        BigInteger[] res = {n, e, crtGetc()};
        return res;
    }

    /**
     * 群管理员获取的内容
     * @param yi
     * @param userId
     */
    private void addGroupManger(BigInteger yi, long userId){
        manageInfoMap.put(userId, yi);
    }

    /**
     * 求得 同余方程组的解
     * @return
     */
    public BigInteger crtGetc(){
        List<GroupMember> gm = memberList;
        int size = gm.size();

        List<BigInteger> ylist = new ArrayList<>();
        List<BigInteger> plist = new ArrayList<>();

        for(int i=0;i<size;i++){
            ylist.add(gm.get(i).getY());
            plist.add(gm.get(i).getPi());
        }
        return new CRT().getRes(ylist, plist);
    }

    /**
     * 使用 SHA-256哈希加密 并把加密之后的信息转换成BigInteger
     * @param msg
     * @return
     */
    public BigInteger MyHash(String msg){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(msg.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        BigInteger res = new BigInteger(generatedPassword, 16);
        return res;
    }

    /**
     * 返回 GroupMember 对象
     * @param index
     * @return
     */
    public GroupMember getgMember(int index){
        return memberList.get(index);
    }
}
