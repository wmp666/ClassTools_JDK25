package com.wmp.classTools.extraPanel.attendance.settings;

import com.wmp.PublicTools.io.IOForInfo;
import com.wmp.PublicTools.printLog.Log;
import com.wmp.classTools.CTComponent.CTPanel.setsPanel.CTTableSetsPanel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AllStuSetsPanel extends CTTableSetsPanel {

    private final File AllStuPath;

    public AllStuSetsPanel(String basicDataPath) {
        super(new String[]{"姓名"}, null, basicDataPath);

        File dataPath = new File(basicDataPath, "Att");
        this.AllStuPath = new File(dataPath, "AllStu.txt");

        setName("学生名单设置");

        setArray(getStudentList());
    }

    private String[][] getStudentList() {
        //ArrayList<JCheckBox> checkBoxList = new ArrayList<>();
        ArrayList<String> studentList = new ArrayList<>();

        //获取所有学生名单
        {
            IOForInfo ioForInfo = new IOForInfo(AllStuPath);

            String[] inf = null;
            try {
                inf = ioForInfo.getInfo();
            } catch (Exception e) {
                Log.err.print(getClass(), "获取失败", e);
            }

            //System.out.println(inf);
            if (!inf[0].equals("err")) {
                studentList.addAll(Arrays.asList(inf));
            }
        }

        String[][] result = new String[studentList.size()][1];
        for (int i = 0; i < studentList.size(); i++) {
            result[i][0] = studentList.get(i);
        }
        return result;
    }

    @Override
    public void save() {
        //保存数据-人员名单
        //处理表格中的数据


        // 遍历表格中的每一行，将每一行的数据添加到tempList中
        StringBuilder sb = new StringBuilder();
        String[][] array = this.getArray();
        for (int i = 1; i < array.length; i++)
            sb.append(array[i][0])
                    .append("\n");


        try {
            new IOForInfo(AllStuPath).setInfo(sb.toString());
        } catch (Exception e) {
            Log.err.print(getClass(), "设置失败", e);
        }


    }

    @Override
    public String[][] resetData() {
        return getStudentList();
    }
}
