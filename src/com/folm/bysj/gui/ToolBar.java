package com.folm.bysj.gui;

import com.folm.bysj.model.GroupSinature;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

public class ToolBar extends JPanel implements ActionListener {
    private JButton addGroupMemberButton;
    private JButton getGroupPubKeyButton; // 获取群公钥
    private JButton getCRTcButton;
    private InfoFrame in;
    private GroupSinature gp;

    public ToolBar(InfoFrame in, GroupSinature gp) {
        this.in = in;
        this.gp = gp;

        setBorder(BorderFactory.createEtchedBorder());
        addGroupMemberButton = new JButton("添加群成员");
        getGroupPubKeyButton = new JButton("获取群公钥");
        getCRTcButton = new JButton("获取c");
        addGroupMemberButton.addActionListener(this);
        getGroupPubKeyButton.addActionListener(this);
        getCRTcButton.addActionListener(this);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(addGroupMemberButton);
        add(getGroupPubKeyButton);
        add(getCRTcButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TextPanel info = in.getTextPanel();
        JButton clicked = (JButton) e.getSource();
        if(clicked.equals(addGroupMemberButton)){
            gp.addMember();
        } else if (clicked.equals(getGroupPubKeyButton)){
            info.appendTextLn("获取群公钥");
            BigInteger[] res = gp.getGroupPubKey();
            info.appendTextLn("n："+res[0]);
            info.appendTextLn("e："+res[1]);
            info.appendTextLn("c："+res[2]);
        } else if (clicked.equals(getCRTcButton)){
            info.appendTextLn("c："+gp.getGroupPubKey()[2]);
        }


    }
}
