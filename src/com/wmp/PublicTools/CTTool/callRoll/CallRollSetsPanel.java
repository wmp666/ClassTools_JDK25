package com.wmp.PublicTools.CTTool.callRoll;

import com.wmp.PublicTools.UITools.CTColor;
import com.wmp.PublicTools.UITools.CTFont;
import com.wmp.PublicTools.UITools.CTFontSizeStyle;
import com.wmp.PublicTools.io.GetPath;
import com.wmp.PublicTools.printLog.Log;
import com.wmp.classTools.CTComponent.CTButton.CTTextButton;
import com.wmp.classTools.CTComponent.CTPanel.setsPanel.CTBasicSetsPanel;
import com.wmp.classTools.CTComponent.CTTextField;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class CallRollSetsPanel extends CTBasicSetsPanel {

    CTTextField countTextField = new CTTextField();

    public CallRollSetsPanel(String basicDataPath) {
        super(basicDataPath);

        setName("点名设置");
    }

    @Override
    public void save() throws Exception {
        CallRollInfoControl.setCount(Integer.parseInt(countTextField.getText()));
    }

    @Override
    public void refresh() {
        this.removeAll();
        this.setLayout(new GridLayout(0, 1));

        CTTextButton inputButton = new CTTextButton("导入");
        inputButton.addActionListener(e1 -> {
            String filePath = GetPath.getFilePath(this, "请选择文件", ".txt", "点名列表");
            if (filePath != null) {
                CallRollInfoControl.setDianMingNameList(filePath);
            }
        });
        this.add(inputButton);

        JPanel countSetsPanel = new JPanel();
        countSetsPanel.setLayout(new GridLayout(1, 0));
        countSetsPanel.setOpaque(false);

        JLabel jLabel = new JLabel("点名次数:");
        jLabel.setFont(CTFont.getCTFont(Font.BOLD, CTFontSizeStyle.SMALL));
        jLabel.setForeground(CTColor.textColor);
        countSetsPanel.add(jLabel);

        countTextField.setText(CallRollInfoControl.getCount() + "");
        countTextField.setFont(CTFont.getCTFont(Font.PLAIN, CTFontSizeStyle.SMALL));
        countTextField.setForeground(CTColor.textColor);
        countTextField.setBackground(CTColor.backColor);
        countSetsPanel.add(countTextField);

        this.add(countSetsPanel);
    }
}
