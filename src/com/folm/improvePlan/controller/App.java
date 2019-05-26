package com.folm.improvePlan.controller;

import com.folm.improvePlan.model.GroupCenter;
import com.folm.improvePlan.model.GroupManager;

import java.util.Arrays;

public class App {

    public static void main(String[] args) {
        GroupCenter gc = new GroupCenter();
        System.out.println(Arrays.toString(gc.getGroupInfo()));

        GroupManager gman = new GroupManager();
        System.out.println(Arrays.toString(gman.getGroupManagerInfo()));
    }
}
