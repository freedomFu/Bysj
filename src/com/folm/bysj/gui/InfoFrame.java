package com.folm.bysj.gui;

import java.awt.*;

/**
 * 信息界面 用于记录信息
 * @author folm
 */
public class InfoFrame extends CommonFrame {

    private TextPanel textPanel;

    public InfoFrame(){
        super("Info Recording", 700, 50);
        textPanel = new TextPanel();
//        textPanel.setState(false);
        textPanel.appendTextLn("程序启动成功！");
        add(textPanel, BorderLayout.CENTER);
    }

    public TextPanel getTextPanel() {
        return textPanel;
    }
}
