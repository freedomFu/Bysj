package com.folm.bysj.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Database {

    private List<GroupMember> gmember;

    public Database() {
        gmember = new LinkedList<>();
    }

    public void addPerson(GroupMember gm){
        gmember.add(gm);
    }

    public void removeGroupMember(int index){
        gmember.remove(index);
        System.out.println(gmember.size());
    }

    public List<GroupMember> getGmember(){
        // 不可修改
        return Collections.unmodifiableList(gmember);
    }

}
