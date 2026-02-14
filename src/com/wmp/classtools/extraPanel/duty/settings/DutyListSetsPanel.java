package com.wmp.classTools.extraPanel.duty.settings;

import com.wmp.publicTools.appFileControl.CTInfoControl;
import com.wmp.publicTools.printLog.Log;
import com.wmp.classTools.CTComponent.CTPanel.setsPanel.CTTableSetsPanel;
import com.wmp.classTools.extraPanel.duty.control.DutyDay;
import com.wmp.classTools.extraPanel.duty.control.DutyInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DutyListSetsPanel extends CTTableSetsPanel<DutyInfo> {

    public DutyListSetsPanel(CTInfoControl<DutyInfo> infoControl) {
        super(new String[]{"扫地", "擦黑板"}, null, infoControl);
        setName("值日生");

        //初始化
        try {
            String[][] dutyList = getDutyList();
            setArray(dutyList);
        } catch (IOException e) {
            Log.err.print(getClass(), "初始化失败", e);
        }

    }

    private String[][] getDutyList() throws IOException {
        return getInfoControl().getInfo().toTableList();
    }

    @Override
    public void save() {
        //保存数据-dutyList


        //处理表格中的数据

        // 遍历表格中的每一行，将每一行的数据添加到tempList中
        //getRowCount()-行数
        //StringBuilder sb = new StringBuilder();

        String[][] array = this.getArray();
        DutyDay[] dutyDayList = new DutyDay[array.length - 1];
        for (int i = 1; i < array.length; i++) {
            dutyDayList[i - 1] =
                    new DutyDay(new ArrayList<>(List.of(array[i][0].split( ","))),
                            new ArrayList<>(List.of(array[i][1].split( ","))));
        }

        getInfoControl().setInfo(new DutyInfo(dutyDayList, -1));
    }

    @Override
    public String[][] resetData() {
        try {
            return getDutyList();
        } catch (IOException e) {
            Log.err.print(getClass(), "重置数据失败", e);
            return null;
        }
    }
}
