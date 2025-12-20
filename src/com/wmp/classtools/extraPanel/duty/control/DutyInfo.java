package com.wmp.classTools.extraPanel.duty.control;

public record DutyInfo(DutyDay[] dutyDay, int index) {
    /**
     * 转换为表格数据
     * @return 表格数据, 第一列为扫地, 第二列为擦黑板
     */
    public String[][] toStringList() {
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
}
