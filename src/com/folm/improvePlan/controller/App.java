package com.folm.improvePlan.controller;

import com.folm.improvePlan.Utils.Exponentiation;
import com.folm.improvePlan.model.GroupCenter;
import com.folm.improvePlan.model.GroupManager;
import com.folm.improvePlan.model.GroupMember;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class App {

    public static void main(String[] args) {
        /*GroupCenter gc = new GroupCenter();
        System.out.println(Arrays.toString(gc.getGroupInfo()));

        GroupManager gman = gc.getGmanager();
        System.out.println("嘤嘤嘤"+gman);
        System.out.println(Arrays.toString(gman.getGroupManagerInfo()));

        gc.addMember();
        gc.addMember();
        gc.addMember();

        GroupMember gm = gc.getMemberRecordList().get(1);

        Object[] yyy = gm.getSiRecord();
        System.out.println(Arrays.toString(yyy));

        System.out.println(gc.getSi());
        BigInteger ng = gman.getNg();
        ArrayList<Object[]> list = gm.getPathNodeList();
        System.out.println(list);
        BigInteger[] idi = gm.getIdi();
        BigInteger idires = new Exponentiation().expMode(idi[0],idi[1],ng);
        System.out.println("这个成员的idi是："+idires);
        Object[] res = gc.getEleCheckNewMemberLegal(idi);
        System.out.println(Arrays.toString(res));
        BigInteger rc = (BigInteger) res[1];
        BigInteger[] wg = gman.getWg(idi);
        System.out.println("wg的值为："+wg);
        boolean flag = gm.isLegal();
        if(flag){
            System.out.println("成功！");
        }else{
            System.out.println("失败");
        }
        Object[] sign = gm.signMsg("ni da qiu xiang caixukun");
        System.out.println(Arrays.toString(sign));
        gman.checkSign(sign);

        BigInteger revokeRes = gman.showSignMemberidi(sign);
        System.out.println("蔡徐坤"+revokeRes);

        System.out.println(gc.getCRTC());
        GroupMember gm1 = gc.getMemberRecordList().get(2);
        gc.revokeMember(gm1);

        System.out.println(gc.getCRTC());
        System.out.println("执行结束");*/

    }

}
