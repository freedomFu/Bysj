package com.folm.improvePlan.gui;

import com.folm.improvePlan.model.GroupCenter;
import com.folm.improvePlan.model.GroupMember;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TablePanel extends JPanel {
    private JTable table;
    private GroupMemberModel groupMemberModel;

    public TablePanel(GroupCenter gc){
        groupMemberModel = new GroupMemberModel(gc);
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
