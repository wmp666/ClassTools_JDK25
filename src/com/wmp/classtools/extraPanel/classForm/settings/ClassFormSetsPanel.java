package com.wmp.classTools.extraPanel.classForm.settings;

import com.wmp.PublicTools.io.IOForInfo;
import com.wmp.PublicTools.printLog.Log;
import com.wmp.classTools.CTComponent.CTPanel.setsPanel.CTBasicSetsPanel;
import com.wmp.classTools.CTComponent.CTPanel.setsPanel.CTListSetsPanel;
import com.wmp.classTools.extraPanel.classForm.settings.week.WeekSetsPanel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ClassFormSetsPanel extends CTListSetsPanel {


    private final String path;

    private final ArrayList<String[][]> CFTableInfoList = new ArrayList<>();

    public ClassFormSetsPanel(String basicDataPath) {
        super(basicDataPath);

        this.path = basicDataPath + "ClassForm\\";
        this.setID("ClassFormSetsPanel");
        this.setName("课程表设置");
        this.setLayout(new BorderLayout());
        initSetsPanel();

        initChooseButtons();
    }

    private void initChooseButtons() {
        Log.info.print("CFSetsPanel", "初始化页面切换");
        this.clearCTList();
        for (int i = 0; i < 7; i++) {
            this.add(resetSetsPanel(i + 1));

        }

        // 强制重新布局和重绘
        this.revalidate();
        this.repaint();
    }

    private void initSetsPanel() {
        Log.info.print("CFSetsPanel", "初始化课程表设置面板");
        CFTableInfoList.clear();
        for (int i = 1; i <= 7; i++) {
            CFTableInfoList.add(getClassFormData(i));
        }
    }

    private CTBasicSetsPanel resetSetsPanel(int week) {
        Log.info.print("CFSetsPanel", "重置课程表设置面板:" + week);

        return new WeekSetsPanel(new String[]{"课程", "时间(周" + week + ")"},
                CFTableInfoList.get(week - 1), new File(path, week + ".json").getAbsolutePath(), week);

    }

    private String[][] getClassFormData(int week) {
        Log.info.print("CFSetsPanel", "获取星期" + week + "课程表数据");

        File path = new File(this.path + week + ".json");

        ArrayList<String> CFList = new ArrayList<>();
        ArrayList<String> dateList = new ArrayList<>();
        try {
            String infos = new IOForInfo(path).getInfos();
            if (infos.equals("err")) return new String[][]{{}, {}};

            JSONArray jsonArray = new JSONArray(infos);
            jsonArray.forEach(object -> {
                if (object instanceof JSONObject jsonObject) {
                    if (!(jsonObject.has("class") && jsonObject.has("time"))) return;
                    CFList.add(jsonObject.getString("class"));
                    dateList.add(jsonObject.getString("time"));
                }
            });
            String[][] temp = new String[CFList.size()][2];
            for (int i = 0; i < CFList.size(); i++) {
                temp[i][0] = CFList.get(i);
                temp[i][1] = dateList.get(i);
            }
            return temp;
        } catch (Exception e) {
            Log.err.print(getClass(), "获取数据失败", e);
        }
        return null;
    }

    @Override
    public void refresh() {
        this.removeAll();

        initSetsPanel();
        initChooseButtons();

        this.revalidate();
        this.repaint();
    }
}
