package com.folm.bysj.gui;

import com.folm.bysj.model.GroupMember;
import com.folm.bysj.model.GroupSinature;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TablePanel extends JPanel {
    private JTable table;
    private GroupMemberModel groupMemberModel;

    public TablePanel(GroupSinature gs){
        groupMemberModel = new GroupMemberModel(gs);
        table = new JTable(groupMemberModel);

        setLayout(new BorderLayout());

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void setData(List<GroupMember> db){
        groupMemberModel.setData(db);
    }

    public void refresh(){
        groupMemberModel.fireTableDataChanged();
    }



}
