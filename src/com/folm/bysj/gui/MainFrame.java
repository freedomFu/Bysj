package com.folm.bysj.gui;

import com.folm.bysj.controller.Controller;
import com.folm.bysj.model.GroupSinature;
import java.awt.*;

/**
 * 主界面 用于执行操作
 * @author folm
 */
public class MainFrame extends CommonFrame {

    private ToolBar toolbar;
    private GroupSinature gp;
    private InfoFrame in;
    private FormPanel formPanel;
    private TablePanel tablePanel;
    private Controller controller;

    public MainFrame(InfoFrame in){
        super("Operating Panel", 50 , 50);
        this.in = in;
        // 获取消息面板对象
        TextPanel tpInfo = in.getTextPanel();
        tpInfo.appendTextLn("开始创建系统中心");
        gp = new GroupSinature(tpInfo);
        formPanel = new FormPanel(gp, in);
        tablePanel = new TablePanel(gp);
        toolbar = new ToolBar(in, gp, tablePanel);
        controller = new Controller();
        tablePanel.setData(gp.getMemberList());

        add(formPanel, BorderLayout.WEST);
        add(toolbar,BorderLayout.NORTH);
        add(tablePanel,BorderLayout.CENTER);
    }

}
