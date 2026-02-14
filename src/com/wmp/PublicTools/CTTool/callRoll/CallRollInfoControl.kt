package com.wmp.publicTools.CTTool.callRoll;

import com.wmp.publicTools.CTInfo;
import com.wmp.publicTools.io.IOForInfo;

import java.io.IOException;
import java.nio.file.Path;

public class CallRollInfoControl {
    private static final String path = CTInfo.DATA_PATH + "CTTools\\DianMing\\";

    public static String[] getDianMingInfo() throws IOException {
        IOForInfo io = new IOForInfo(path + "DianMingInfo.txt");
        String[] info = io.getInfo();
        if (info == null || info.length == 0 || info[0].equals("err")) {
            return null;
        }
        return info;
    }

    public static void setDianMingInfo(String[] info) throws IOException {
        IOForInfo io = new IOForInfo(path + "DianMingInfo.txt");
        io.setInfo(info);

    }

    public static void setDianMingNameList(String path) {
        IOForInfo.copyFile(Path.of(path), Path.of(CallRollInfoControl.path));
    }
}
