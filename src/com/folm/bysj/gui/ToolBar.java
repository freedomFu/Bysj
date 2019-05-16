package com.folm.bysj.gui;

import com.folm.bysj.model.GroupSinature;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolBar extends JPanel implements ActionListener {
    private JButton addGroupMemberButton;
    private InfoFrame in;
    private  GroupSinature gp;

    public ToolBar(InfoFrame in, GroupSinature gp) {
        this.in = in;
        this.gp = gp;

        setBorder(BorderFactory.createEtchedBorder());
        addGroupMemberButton = new JButton("添加群成员");
        addGroupMemberButton.addActionListener(this);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(addGroupMemberButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TextPanel info = in.getTextPanel();
        gp.addMember();

    }
}
