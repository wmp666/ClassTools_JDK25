package com.wmp.classTools.extraPanel.attendance.settings;

import com.wmp.publicTools.appFileControl.CTInfoControl;
import com.wmp.classTools.CTComponent.CTPanel.setsPanel.CTTableSetsPanel;
import com.wmp.classTools.extraPanel.attendance.control.AttInfo;

import java.util.ArrayList;

public class AllStuSetsPanel extends CTTableSetsPanel<AttInfo> {

    public AllStuSetsPanel(CTInfoControl<AttInfo> infoControl) {
        super(new String[]{"姓名"}, null, infoControl);


        setName("学生名单设置");

        setArray(getStudentList());
    }

    private String[][] getStudentList() {

        String[] allStuList = getInfoControl().getInfo().allStuList();

        String[][] result = new String[allStuList.length][1];
        for (int i = 0; i < allStuList.length; i++) {
            result[i][0] = allStuList[i];
        }
        return result;
    }

    @Override
    public void save() {
        //保存数据-人员名单
        //处理表格中的数据


        // 遍历表格中的每一行，将每一行的数据添加到tempList中
        ArrayList<String> tempList = new ArrayList<>();
        String[][] array = this.getArray();
        for (int i = 1; i < array.length; i++)
            tempList.add(array[i][0]);

        getInfoControl().setInfo(new AttInfo(tempList.toArray(new String[0]), null));


    }

    @Override
    public String[][] resetData() {
        return getStudentList();
    }
}
