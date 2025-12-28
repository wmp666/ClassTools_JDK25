package com.wmp.classTools.extraPanel.duty.control;

import java.util.Arrays;

public record DutyInfo(DutyDay[] dutyDay, int index) {
    /**
     * 转换为表格数据
     * @return 表格数据, 第一列为扫地, 第二列为擦黑板
     */
    public String[][] toTableList() {
        String[][] list = new String[dutyDay.length][2];
        for (int i = 0; i < dutyDay.length; i++) {
            {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < dutyDay[i].getClFloorList().size(); j++) {
                    sb.append(dutyDay[i].getClFloorList().get(j));
                    if (j != dutyDay[i].getClFloorList().size() - 1) {
                        sb.append(",");
                    }
                }
                list[i][0] = sb.toString();
            }
            {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < dutyDay[i].getClBlackBroadList().size(); j++) {
                    sb.append(dutyDay[i].getClBlackBroadList().get(j));
                    if (j != dutyDay[i].getClBlackBroadList().size() - 1) {
                        sb.append(",");
                    }
                }
                list[i][1] = sb.toString();
            }
        }
        return list;
    }

    @Override 
    public String toString() {
        return  "值日生列表:" + Arrays.toString(dutyDay)  + "|索引:" + index;
    }
}
