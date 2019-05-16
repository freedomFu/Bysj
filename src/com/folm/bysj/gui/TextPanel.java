package com.folm.bysj.gui;

import javax.swing.*;
import java.awt.*;

/**
 * 文本部分设计  用于信息展示
 */
public class TextPanel extends JPanel {

    private JTextArea textArea;

    public TextPanel() {
        textArea = new JTextArea();
        setLayout(new BorderLayout());
        setLayout(new BorderLayout());
        textArea.setFont(new Font("宋体",Font.ITALIC, 15));
        textArea.setForeground(Color.RED);

        // 添加滚动条
        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    public void appendTextLn(String text){
        textArea.append(text);
        textArea.append("\n");
        textArea.setCaretPosition(textArea.getText().length());
    }

    public void appendText(String text){
        textArea.append(text);
        textArea.setCaretPosition(textArea.getText().length());
    }

    public void setState(boolean state){
        textArea.setEnabled(state);
    }

}
