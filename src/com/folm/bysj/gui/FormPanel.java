package com.folm.bysj.gui;

import com.folm.bysj.model.GroupSinature;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormPanel extends JPanel{

    private JLabel nameLabel;
    private JTextField nameField;
    private JButton okBtn;
    private GroupSinature gp;
    private InfoFrame in;

    public FormPanel(GroupSinature gp, InfoFrame in){
        this.gp = gp;
        this.in = in;
        Dimension dim = getPreferredSize();
        dim.width = 250;
        setPreferredSize(dim);

        nameLabel = new JLabel("Msg: ");
        nameField = new JTextField(10);

        okBtn = new JButton("OK");

        // 添加到界面上 使用工具
        Border innerBorder = BorderFactory.createTitledBorder("Msg Signature");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        layoutComponents();

        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = nameField.getText();
                if(null != s && !s.equals("")){
                    TextPanel tp = in.getTextPanel();
                    tp.appendTextLn(s);
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
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(okBtn, gc);
    }
}
