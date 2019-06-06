package com.folm.improvePlan.gui;

import com.folm.improvePlan.model.GroupCenter;
import com.folm.improvePlan.model.GroupMember;

import javax.swing.table.AbstractTableModel;
import java.math.BigInteger;
import java.util.List;

public class GroupMemberModel extends AbstractTableModel {

    private List<GroupMember> db;
    private GroupCenter gc;
    // 设置首行
    private String[] colNames = {"ID", "Xi,j", "rc", "sc"};

    public GroupMemberModel(GroupCenter gc){
        this.gc = gc;
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
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        GroupMember gmember = db.get(rowIndex);
        BigInteger[] idi = gmember.getIdi();
        switch (columnIndex){
            case 0:
                return gmember.getUserId();
            case 1:
                return "("+gmember.getXy()[0]+", "+gmember.getXy()[1]+")";
            case 2:
                return gc.getrcSc(idi)[0];
            case 3:
                return gc.getrcSc(idi)[0];
        }

        return null;
    }
}
