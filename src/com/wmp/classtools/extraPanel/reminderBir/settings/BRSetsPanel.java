package com.wmp.classTools.extraPanel.reminderBir.settings;

import com.wmp.PublicTools.io.IOForInfo;
import com.wmp.PublicTools.printLog.Log;
import com.wmp.classTools.CTComponent.CTOptionPane;
import com.wmp.classTools.CTComponent.CTPanel.setsPanel.CTTableSetsPanel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class BRSetsPanel extends CTTableSetsPanel {

    private final File birthdayPath;

    public BRSetsPanel(String basicDataPath) {
        super(new String[]{"姓名", "日期"}, null, basicDataPath);
        setName("生日表");

        birthdayPath = new File(basicDataPath, "birthday.json");

        setArray(getData());
    }

    @Override
    public String[] addToTable() {

        String name = Log.info.showInputDialog(this, "BRSetsPanel-新建", "请输入姓名");
        if (name == null || name.trim().isEmpty()) return null;

        String style = Log.info.showChooseDialog(this, "BRSetsPanel-新建", "请选择日历形式", "公历", "农历");
        if (style == null || style.trim().isEmpty()) return null;

        int[] times = Log.info.showTimeChooseDialog(this, "BRSetsPanel-新建", "请输入时间", CTOptionPane.MONTH_DAY);
        if (times.length != 2) return null;
        String date = (style.equals("农历") ? "lunar" : "") + times[0] + "-" + times[1];

        return new String[]{name, date};
    }

    private String[][] getData() {
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<String> dateList = new ArrayList<>();
        if (!birthdayPath.exists()) return new String[][]{{}, {}};
        try {
            String infos = IOForInfo.getInfos(birthdayPath.toURI().toURL());
            JSONArray jsonArray = new JSONArray(infos);
            jsonArray.forEach(object -> {
                if (object instanceof JSONObject jsonObject) {
                    nameList.add(jsonObject.getString("name"));
                    dateList.add(jsonObject.getString("birthday"));
                }
            });
            String[][] temp = new String[nameList.size()][2];
            for (int i = 0; i < nameList.size(); i++) {
                temp[i][0] = nameList.get(i);
                temp[i][1] = dateList.get(i);
            }
            return temp;
        } catch (Exception e) {
            Log.err.print(getClass(), "获取生日数据失败", e);
        }
        return null;
    }

    @Override
    public String[] removeToTable(String[] oldArray) {
        String name = Log.info.showInputDialog(this, "BRSetsPanel-修改",
                String.format("原数据:%s\n请输入姓名\n注:若不修改不用输入内容", oldArray[0]));
        if (name == null || name.trim().isEmpty()) name = oldArray[0];

        String oldStyle = oldArray[1].startsWith("lunar")?"农历":"公历";
        String style = Log.info.showChooseDialog(this, "BRSetsPanel-修改",
                String.format("原数据:%s\n请选择日历形式\n注:若不修改不用输入内容", oldStyle),  "公历", "农历");
        if (style == null || style.trim().isEmpty()) style = oldStyle;

        int[] oldTimes = new int[2];
        if (oldStyle.equals("农历")){
            String temp = oldArray[1].substring(5);
            String[] split = temp.split("-");
            oldTimes[0] = Integer.parseInt(split[0]);
            oldTimes[1] = Integer.parseInt(split[1]);
        }
        int[] times = Log.info.showTimeChooseDialog(this, "BRSetsPanel-修改", "请输入时间", CTOptionPane.MONTH_DAY, oldTimes);
        String date = (style.equals("农历") ? "lunar" : "") + times[0] + "-" + times[1];

        return new String[]{name, date};
    }

    @Override
    public String[][] resetData() {
        return getData();
    }


    @Override
    public void save() throws Exception {
        IOForInfo ioForInfo = new IOForInfo(birthdayPath);

        //将表格中的数组转化成JsonArray
        JSONArray jsonArray = new JSONArray();
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<String> dateList = new ArrayList<>();

        String[][] array = this.getArray();
        for (int i = 1; i < array.length; i++) {
            nameList.add(array[i][0]);
            dateList.add(array[i][1]);
        }


        nameList.forEach(s -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", s);
            jsonObject.put("birthday", dateList.get(nameList.indexOf(s)));
            jsonArray.put(jsonObject);
        });
        ioForInfo.setInfo(jsonArray.toString(4));
    }

}
