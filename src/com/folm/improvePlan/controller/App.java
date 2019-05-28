package com.folm.improvePlan.controller;

import com.folm.improvePlan.Utils.CreateBigPrime;
import com.folm.improvePlan.Utils.Exponentiation;
import com.folm.improvePlan.model.GroupCenter;
import com.folm.improvePlan.model.GroupManager;
import com.folm.improvePlan.model.GroupMember;

import java.math.BigInteger;
import java.util.Arrays;

public class App {

    public static void main(String[] args) {
        GroupCenter gc = new GroupCenter();
        System.out.println(Arrays.toString(gc.getGroupInfo()));

        GroupManager gman = new GroupManager(gc);
        System.out.println(Arrays.toString(gman.getGroupManagerInfo()));

        gc.addMember();
        gc.addMember();
        gc.addMember();

        GroupMember gm = gc.getMemberRecordList().get(1);
        gm.getCertificate();
        BigInteger idi = gm.getIdi();
        BigInteger[] res = gc.getEleCheckNewMemberLegal(idi);
        System.out.println(Arrays.toString(res));
        BigInteger rc = res[1];
        BigInteger wg = gman.getWg(idi, rc);
        System.out.println("wg的值为："+wg);
        boolean flag = gm.isLegal(rc);
        if(flag){
            System.out.println("成功！");
        }else{
            System.out.println("失败");
        }

        System.out.println(gc.getCRTC());
        GroupMember gm1 = gc.getMemberRecordList().get(2);
        gc.revokeMember(gm1);

        System.out.println(gc.getCRTC());


        System.out.println("执行结束");

    }

}
