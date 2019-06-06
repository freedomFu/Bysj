package com.folm.improvePlan.gui;

import com.folm.improvePlan.model.GroupCenter;
import com.folm.improvePlan.model.GroupManager;

import java.awt.*;

/**
 * 主界面 用于执行操作
 * @author folm
 */
public class MainFrame extends CommonFrame {

    private ToolBar toolbar;
    private GroupCenter gc;
    private InfoFrame in;
    private FormPanel formPanel;
    private TablePanel tablePanel;
    private GroupManager gman;

    public MainFrame(InfoFrame in){
        super("Operating Panel", 50 , 50);

        // 获取消息面板对象
        TextPanel tpInfo = in.getTextPanel();
        tpInfo.appendTextLn("开始创建系统中心");
        gc = new GroupCenter(tpInfo);
        gman = gc.getGmanager();
        this.in = in;

        formPanel = new FormPanel(gc, in);
        tablePanel = new TablePanel(gc);
        toolbar = new ToolBar(in, gc, tablePanel);
        tablePanel.setData(gc.getMemberRecordList());

        add(formPanel, BorderLayout.WEST);
        add(toolbar,BorderLayout.NORTH);
        add(tablePanel,BorderLayout.CENTER);
    }

}
