package com.folm.bysj.gui;

import com.folm.bysj.model.GroupManager;
import com.folm.bysj.model.GroupMember;
import com.folm.bysj.model.GroupSinature;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.Random;
import java.util.List;

public class FormPanel extends JPanel{

    private JLabel nameLabel;
    private JTextField nameField;
    private JButton okBtn;
    private JButton checkBtn;
    private JButton openSign;
    private GroupSinature gp;
    private InfoFrame in;
    private List<GroupMember> lists;
    private int x;

    public FormPanel(GroupSinature gp, InfoFrame in){
        this.gp = gp;
        this.in = in;
        Dimension dim = getPreferredSize();
        dim.width = 250;
        setPreferredSize(dim);
        lists = gp.getMemberList();

        nameLabel = new JLabel("Msg: ");
        nameField = new JTextField(10);

        okBtn = new JButton("Sign");
        checkBtn = new JButton("Check");
        openSign = new JButton("Open");
        x = (int)((new Random().nextDouble())*65537.0);


        // 添加到界面上 使用工具
        Border innerBorder = BorderFactory.createTitledBorder("Msg Signature");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        layoutComponents();

        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(x);
                String s = nameField.getText();
                if(null != s && !s.equals("")){
                    int size = lists.size();
                    if(size>0){
                        x = x%size;
                        TextPanel tp = in.getTextPanel();
                        // 随机取出一个对象进行签名：
                        GroupMember gm = lists.get(x);
                        Object[] res = gm.sign(gp, s);
                        tp.appendTextLn("群成员签名成功！（隐藏的x是："+x+"）");
                        tp.appendTextLn((String)res[0]);
                        tp.appendTextLn("产生的si："+res[1]);
                        tp.appendTextLn("存储的pi："+res[2]);
                    }else{
                        JOptionPane.showMessageDialog(null, "请先添加群成员", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "请输入消息", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        checkBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(x);
                String s = nameField.getText();
                if(null != s && !s.equals("")){
                    int size = lists.size();
                    if(size>0){
                        x = x%size;
                        TextPanel tp = in.getTextPanel();
                        // 群管理员
                        GroupManager gman = new GroupManager();
                        GroupMember gm = lists.get(x);
                        boolean flag = gman.isSignCorrect(gp, gm, s);
                        if(flag){
                            tp.appendTextLn("验证签名成功！");
                        }else{
                            tp.appendTextLn("验证签名失败！");
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "请先添加群成员", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "请输入消息", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        openSign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(x);
                String s = nameField.getText();
                if(null != s && !s.equals("")){
                    int size = lists.size();
                    if(size>0){
                        x = x%size;
                        TextPanel tp = in.getTextPanel();
                        // 群管理员
                        GroupManager gman = new GroupManager();
                        GroupMember gm = lists.get(x);
                        long id = gman.openSign(gp, gm, s);
                        tp.appendTextLn("打开签名成功！");
                        tp.appendTextLn("加密的成员id是："+id);
                    }else{
                        JOptionPane.showMessageDialog(null, "请先添加群成员", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "请输入消息", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void layoutComponents(){
        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        /******************* First  Row ****************/
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.gridx = 0;

        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        add(nameLabel, gc);

        gc.gridx = 1;
        gc.gridy = 0;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        add(nameField, gc);

        /******************* Next  Row ****************/
        gc.gridy++;
        gc.weightx = 1;
        gc.weighty = 2.0;

        gc.gridx = 1;
        gc.insets = new Insets(10, 0, 0, 0);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(okBtn, gc);

        /******************* Next  Row ****************/
        gc.gridy++;
        gc.weightx = 1;
        gc.weighty = 2.2;

        gc.gridx = 1;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(checkBtn, gc);

        /******************* Next  Row ****************/
        gc.gridy++;
        gc.weightx = 1;
        gc.weighty = 2.4;

        gc.gridx = 1;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(openSign, gc);
    }
}
