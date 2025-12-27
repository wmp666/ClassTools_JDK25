package com.wmp.classTools.extraPanel.duty.control;

import com.wmp.PublicTools.CTInfo;
import com.wmp.PublicTools.appFileControl.CTInfoControl;
import com.wmp.PublicTools.io.IOForInfo;
import com.wmp.PublicTools.io.InfProcess;
import com.wmp.PublicTools.printLog.Log;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DutyControl extends CTInfoControl<DutyInfo> {
    @Override
    public File getInfoBasicFile() {
        return new File(CTInfo.DATA_PATH, "Duty");
    }

    @Override
    public void setInfo(DutyInfo dutyInfo) {
        try {
            if (dutyInfo.dutyDay() != null) {
                StringBuilder sb = new StringBuilder();
                String[][] array = dutyInfo.toStringList();
                for (int i = 1; i < array.length; i++) {

                    //getColumnCount()-列数
                    for (int j = 0; j < array[i].length; j++) {

                        sb.append("[").append(array[i][j]).append("]");
                    }
                    sb.append("\n");
                }

                new IOForInfo(new File(getInfoBasicFile(), "DutyList.txt")).setInfo(sb.toString());
            }
            if (dutyInfo.index() != -1) {
                new IOForInfo(new File(getInfoBasicFile(), "index.txt")).setInfo(String.valueOf(dutyInfo.index()));
            }
        } catch (IOException e) {
            Log.err.print(getClass(), "保存失败", e);
        }
        refresh();
    }

    @Override
    protected DutyInfo refreshInfo() {
        try {
            DutyDay[] dutyDays;
            int index = 0;
            //刷新dutyList
            {
                //获取inf
                IOForInfo ioForInfo = new IOForInfo(new File(getInfoBasicFile(), "DutyList.txt"));

                String[] inftempList = ioForInfo.getInfo();

                //System.out.println(inf);
                if (inftempList[0].equals("err")) {
                    return new DutyInfo(new DutyDay[]{
                            new DutyDay(new ArrayList<>(List.of("无")),
                                    new ArrayList<>(List.of("无")))
                    }, 0);
                }


                //处理inf

                //初级数据-[0]"[xxx][xxx,xxx]" [1]...

                dutyDays = new DutyDay[inftempList.length];

                int i = 0;
                for (String s : inftempList) {
                    //二级数据- [0]"xxx" [1]"xxx,xxx"
                    //三级数据- [0]["xxx"] [1]["xxx","xxx"]
                    ArrayList<String> strings = InfProcess.NDExtractNames(s);

                    if (strings.size() == 2) {
                        dutyDays[i] = new DutyDay(DutyDay.setDutyPersonList(strings.get(0).split(",")),
                                DutyDay.setDutyPersonList(strings.get(1).split(",")));
                    }
                    i++;
                }
            }
            //刷新index
            {
                IOForInfo ioForInfo = new IOForInfo(new File(getInfoBasicFile(), "index.txt"));
                String inf = ioForInfo.getInfos();
                index = Integer.parseInt(inf);
            }
            return new DutyInfo(dutyDays, index);
        } catch (Exception e) {
            Log.err.print(getClass(), "刷新失败", e);
        }
        return new DutyInfo(new DutyDay[]{
                new DutyDay(new ArrayList<>(List.of("无")),
                        new ArrayList<>(List.of("无")))
        }, 0);
    }
}
