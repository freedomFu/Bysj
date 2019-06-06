package com.folm.improvePlan.gui;

import javax.swing.*;

/**
 * 应用方法
 * 产生两个面板，一个用于执行操作，另外一个进行信息记录
 * @author folm
 */
public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable( ) {
            @Override
            public void run() {
                InfoFrame in = new InfoFrame();
                new MainFrame(in);
            }
        });
    }

}
