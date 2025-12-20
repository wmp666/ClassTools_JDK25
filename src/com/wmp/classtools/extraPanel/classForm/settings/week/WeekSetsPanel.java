package com.wmp.classTools.extraPanel.classForm.settings.week;

import com.wmp.PublicTools.DateTools;
import com.wmp.PublicTools.appFileControl.CTInfoControl;
import com.wmp.PublicTools.io.IOForInfo;
import com.wmp.PublicTools.printLog.Log;
import com.wmp.classTools.CTComponent.CTOptionPane;
import com.wmp.classTools.CTComponent.CTPanel.setsPanel.CTTableSetsPanel;
import com.wmp.classTools.extraPanel.classForm.ClassFormInfos;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

public class WeekSetsPanel extends CTTableSetsPanel<ClassFormInfos[]> {

    private int week = 0;

    public WeekSetsPanel(String[] titleArray, String[][] array, CTInfoControl<ClassFormInfos[]> infoControl, int week) {
        super(titleArray, array, infoControl);
        setName(switch (week){
            case 1-> "周一";
            case 2-> "周二";
            case 3-> "周三";
            case 4-> "周四";
            case 5-> "周五";
            case 6-> "周六";
            case 7-> "周日";
            default -> "周?";
        });

        this.week = week;
    }

    @Override
    public String[] addToTable() {
        String className = Log.info.showInputDialog(this, "WeekSetsPanel-新建", "请输入课程");
        if (className == null || className.trim().isEmpty()) return null;

        int[] beginTimes = Log.info.showTimeChooseDialog(this, "CFSetsPanel-新建", "请选择开始时间", CTOptionPane.HOURS_MINUTES);
        int[] afterTimes = Log.info.showTimeChooseDialog(this, "CFSetsPanel-新建", "请选择结束时间", CTOptionPane.HOURS_MINUTES);
        if (beginTimes == null || afterTimes== null) return null;
        String date = DateTools.getTimeStr(beginTimes, CTOptionPane.HOURS_MINUTES, ':') + "-" + DateTools.getTimeStr(afterTimes, CTOptionPane.HOURS_MINUTES, ':');

        return new String[]{className, date};
    }

    @Override
    public String[] removeToTable(String[] oldArray) {
        //对数据解码
        String oldName = oldArray[0];
        String[] oldDate = oldArray[1].split("-");
        int[] oldBeginTimes = new int[2];
        int[] oldAfterTimes = new int[2];

        {
            String[] tempStr = oldDate[0].split(":");
            for (int i = 0; i < tempStr.length; i++) {
                oldBeginTimes[i] = Integer.parseInt(tempStr[i]);
            }
        }
        {
            String[] tempStr = oldDate[1].split(":");
            for (int i = 0; i < tempStr.length; i++) {
                oldAfterTimes[i] = Integer.parseInt(tempStr[i]);
            }
        }



        String className = Log.info.showInputDialog(this, "WeekSetsPanel-新建",
                String.format("原数据:%s\n请输入课程\n注:若不修改不用输入内容", oldName));
        if (className == null || className.trim().isEmpty()) return null;

        int[] beginTimes = Log.info.showTimeChooseDialog(this, "CFSetsPanel-新建", "请选择开始时间", CTOptionPane.HOURS_MINUTES, oldBeginTimes);
        int[] afterTimes = Log.info.showTimeChooseDialog(this, "CFSetsPanel-新建", "请选择结束时间", CTOptionPane.HOURS_MINUTES, oldAfterTimes);
        if (beginTimes == null || afterTimes== null) return null;
        String date = DateTools.getTimeStr(beginTimes, CTOptionPane.HOURS_MINUTES, ':') + "-" + DateTools.getTimeStr(afterTimes, CTOptionPane.HOURS_MINUTES, ':');

        return new String[]{className, date};
    }

    @Override
    public String[][] resetData() {
        return null;
    }

    @Override
    public void save() throws Exception {
        Log.info.print("WeekSetsPanel", "保存课程表设置" + "周" + week);
        ClassFormInfos classFormInfos = getInfoControl().getInfo()[week - 1];
        ClassFormInfos[] result = new ClassFormInfos[7];
        for (int i = 0; i < 7; i++) {
            if (i != week - 1) result[i] = null;
            else result[i] = classFormInfos;
        }
        getInfoControl().setInfo(result);
    }
}
