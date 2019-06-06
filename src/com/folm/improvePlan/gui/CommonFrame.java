package com.folm.improvePlan.gui;

import javax.swing.*;
import java.awt.*;

public class CommonFrame extends JFrame {

    public CommonFrame(String title, int x, int y) {
        super(title);
        // 设置布局样式
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(500, 400));
        setBounds(x,y,600,500);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
