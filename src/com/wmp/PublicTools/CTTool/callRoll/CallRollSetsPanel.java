package com.wmp.PublicTools.CTTool.callRoll;

import com.wmp.PublicTools.io.GetPath;
import com.wmp.classTools.CTComponent.CTButton.CTTextButton;
import com.wmp.classTools.CTComponent.CTPanel.setsPanel.CTBasicSetsPanel;

import java.awt.*;

public class CallRollSetsPanel extends CTBasicSetsPanel<Object> {
    public CallRollSetsPanel() {
        super(null);

        setName("点名设置");
    }

    @Override
    public void save() throws Exception {
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

    }
}
