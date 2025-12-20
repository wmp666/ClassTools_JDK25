package com.wmp.classTools.infSet.panel.personalizationSets;

import com.wmp.PublicTools.CTInfo;
import com.wmp.PublicTools.UITools.CTColor;
import com.wmp.PublicTools.io.IOForInfo;
import com.wmp.PublicTools.printLog.Log;
import com.wmp.classTools.CTComponent.CTPanel.setsPanel.CTBasicSetsPanel;
import com.wmp.classTools.CTComponent.CTSpinner;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PAppInfoSetsPanel extends CTBasicSetsPanel {
    private final CTSpinner SSMDWaitTimeSpinner = new CTSpinner(new SpinnerNumberModel(0, 0, 60, 1));

    public PAppInfoSetsPanel(String basicDataPath) {
        super(basicDataPath);
        setName("应用信息设置");

        initUI();

    }

    private void initUI() {
        this.setBackground(CTColor.backColor);
        this.setLayout(new GridLayout(0, 1, 5, 5));

        JPanel waitTimePanel = new JPanel();
        waitTimePanel.setOpaque(false);
        waitTimePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel("SSMD等待时间: ");
        label.setForeground(CTColor.textColor);

        IOForInfo io = new IOForInfo(new File(CTInfo.DATA_PATH + "appFileInfo.json"));
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(io.getInfos());
        } catch (Exception e) {
            jsonObject = new JSONObject();
        }

        if (jsonObject.has("SSMDWaitTime")) {
            SSMDWaitTimeSpinner.setValue(jsonObject.get("SSMDWaitTime"));
        }
        waitTimePanel.add(label);
        waitTimePanel.add(SSMDWaitTimeSpinner);

        this.add(waitTimePanel);
    }
    @Override
    public void save() {
        //保存数据-个性化

        IOForInfo io = new IOForInfo(new File(CTInfo.DATA_PATH + "appFileInfo.json"));
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(io.getInfos());
        } catch (Exception e) {
            jsonObject = new JSONObject();
        }

        jsonObject.put("SSMDWaitTime", SSMDWaitTimeSpinner.getValue());


        Log.info.print("InfSetDialog", "保存数据: " + jsonObject);
        try {
            io.setInfo(jsonObject.toString(2));

        } catch (Exception e) {
            Log.err.print(PersonalizationPanel.class, "保存数据失败", e);
        }
    }

    @Override
    public void refresh() {
        this.removeAll();

        initUI();

        this.revalidate();
        this.repaint();
    }
}
