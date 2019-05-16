package com.folm.bysj.controller;

import com.folm.bysj.model.Database;
import com.folm.bysj.model.GroupMember;

import java.util.List;

public class Controller {

    Database db = new Database();

    public List<GroupMember> getGMember(){
        return db.getGmember();
    }

    public void removeGMember(int index){
        db.removeGroupMember(index);
    }

}
