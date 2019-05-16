package com.folm.bysj.gui;

import com.folm.bysj.model.GroupSinature;

import javax.swing.*;

public class TablePanel extends JPanel {
    private JTable table;
    private GroupMemberModel groupMemberModel;

    public TablePanel(GroupSinature gs){
        table = new JTable();
        groupMemberModel = new GroupMemberModel(gs);
    }



}
