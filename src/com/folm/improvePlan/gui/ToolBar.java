package com.folm.improvePlan.gui;
import com.folm.improvePlan.model.GroupCenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.Arrays;

public class ToolBar extends JPanel implements ActionListener {
    private JButton addGroupMemberButton;
    private JButton getGroupPubKeyButton; // 获取群公钥
    private JButton getCRTcButton;
    private JButton getSiButton;
    private InfoFrame in;
    private GroupCenter gc;
    private TablePanel tablePanel;

    public ToolBar(InfoFrame in, GroupCenter gc, TablePanel tablePanel) {
        this.in = in;
        this.gc = gc;
        this.tablePanel = tablePanel;

        setBorder(BorderFactory.createEtchedBorder());
        addGroupMemberButton = new JButton("添加群成员");
        getGroupPubKeyButton = new JButton("获取群公钥");
        getCRTcButton = new JButton("获取c");
        getSiButton = new JButton("获取Si集合");
        addGroupMemberButton.addActionListener(this);
        getGroupPubKeyButton.addActionListener(this);
        getCRTcButton.addActionListener(this);
        getSiButton.addActionListener(this);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(addGroupMemberButton);
        add(getGroupPubKeyButton);
        add(getCRTcButton);
        add(getSiButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TextPanel info = in.getTextPanel();
        JButton clicked = (JButton) e.getSource();
        if(clicked.equals(addGroupMemberButton)){
            boolean flag =  gc.addMember();
            if(flag){
                tablePanel.refresh();
            }else{
                JOptionPane.showMessageDialog(null, "群成员已满", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (clicked.equals(getGroupPubKeyButton)){
            info.appendTextLn("获取群公开");
            info.appendTextLn("====================================");
            info.appendTextLn("nc："+gc.getNc());
            info.appendTextLn("yc："+gc.getYc());
            info.appendTextLn("ec："+gc.getEc());
            info.appendTextLn("g："+gc.getG());
            info.appendTextLn("idc："+gc.getIdc());
            info.appendTextLn("====================================");
        } else if (clicked.equals(getCRTcButton)){
            info.appendTextLn("c："+gc.getCRTC());
        } else if (clicked.equals(getSiButton)){
            info.appendTextLn("si："+gc.getSi());
        }


    }
}
