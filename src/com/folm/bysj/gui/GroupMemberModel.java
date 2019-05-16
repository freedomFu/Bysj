package com.folm.bysj.gui;

import com.folm.bysj.model.GroupMember;
import com.folm.bysj.model.GroupSinature;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class GroupMemberModel extends AbstractTableModel {

    private List<GroupMember> db;
    private GroupSinature gs;
    // 设置首行
    private String[] colNames = {"ID", "Ni"};

    public GroupMemberModel(GroupSinature gs){
        this.gs = gs;
    }

    public void setData(List<GroupMember> db){
        this.db = db;
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public int getRowCount() {
        return db.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        GroupMember gmember = db.get(rowIndex);

        switch (columnIndex){
            case 0:
                return gmember.getUserId();
            case 1:
                return gmember.getNi(gs);
        }

        return null;
    }
}
