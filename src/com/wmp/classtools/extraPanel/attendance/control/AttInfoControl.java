package com.wmp.classTools.extraPanel.attendance.control;

import com.wmp.PublicTools.CTInfo;
import com.wmp.PublicTools.appFileControl.CTInfoControl;
import com.wmp.PublicTools.io.IOForInfo;
import com.wmp.PublicTools.printLog.Log;

import java.io.File;
import java.io.IOException;

public class AttInfoControl extends CTInfoControl<AttInfo> {
    @Override
    public File getInfoBasicFile() {
        return new File(CTInfo.DATA_PATH, "Att");
    }

    @Override
    public void setInfo(AttInfo attInfo) {
        try {
            if (attInfo.allStuList() != null){
                new IOForInfo(new File(getInfoBasicFile(), "AllStu.txt")).setInfo(attInfo.allStuList());
            } else if (attInfo.leaveList() != null) {
                new IOForInfo(new File(getInfoBasicFile(), "LeaveList.txt")).setInfo(attInfo.leaveList());
            }
        } catch (IOException e) {
            Log.err.print(getClass(), "保存失败", e);
        }
    }

    @Override
    public AttInfo refresh() {
        try {
            return new AttInfo(new IOForInfo(new File(getInfoBasicFile(), "AllStu.txt")).getInfo(),
                    new IOForInfo(new File(getInfoBasicFile(), "LeaveList.txt")).getInfo());
        } catch (IOException e) {
            Log.err.print(getClass(), "获取失败", e);
        }
        return new AttInfo(new String[]{"异常"}, new String[]{"异常"});
    }
}
