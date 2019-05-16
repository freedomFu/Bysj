package com.folm.bysj.gui;

import com.folm.bysj.model.GroupSinature;
import java.awt.*;
import java.math.BigInteger;

/**
 * 主界面 用于执行操作
 * @author folm
 */
public class MainFrame extends CommonFrame {

    private ToolBar toolbar;
    private GroupSinature gp;
    private InfoFrame in;
    private FormPanel formPanel;

    public MainFrame(InfoFrame in){
        super("Operating Panel", 50 , 50);
        this.in = in;
        // 获取消息面板对象
        TextPanel tpInfo = in.getTextPanel();
        tpInfo.appendTextLn("开始创建系统中心");
        gp = new GroupSinature(tpInfo);
        toolbar = new ToolBar(in, gp);
        formPanel = new FormPanel(gp, in);
        add(formPanel, BorderLayout.WEST);
        add(toolbar,BorderLayout.NORTH);
    }

}
