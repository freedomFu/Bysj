package com.folm.improvePlan.controller;

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

//        gc.addMember();
//        gc.addMember();
//        gc.addMember();
//
//        GroupMember gm = gc.getMemberRecordList().get(1);
//        BigInteger idi = gm.getIdi();

        BigInteger g = gc.getG();
        BigInteger k = new BigInteger("5");
        BigInteger idi = new Exponentiation().expMode(g,k);

        System.out.println(idi);
        System.out.println(String.valueOf(idi));
        gc.getEleCheckNewMemberLegal(idi);

//        System.out.println(gc.getMemberRecordList());

    }

}
