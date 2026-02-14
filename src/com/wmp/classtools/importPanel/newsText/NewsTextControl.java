package com.wmp.classTools.importPanel.newsText;

import com.wmp.publicTools.CTInfo;
import com.wmp.publicTools.appFileControl.CTInfoControl;
import com.wmp.publicTools.io.IOForInfo;
import com.wmp.publicTools.printLog.Log;

import java.io.File;
import java.io.IOException;

public class NewsTextControl extends CTInfoControl<String> {
    protected String refreshInfo() {
        IOForInfo io = new IOForInfo(new File(getInfoBasicFile(), "key.txt").getAbsolutePath());
        try {
            String infos = io.getInfos();
            if (infos == null || infos.equals("err")) {
                return "";
            }
            return infos;
        } catch (IOException e) {
            Log.err.print(NewsTextControl.class, "新闻文本获取密钥获取失败", e);
        }
        return null;
    }

    @Override
    public File getInfoBasicFile() {
        return new File(CTInfo.DATA_PATH, "NewsText");
    }

    public void setInfo(String key) {
        IOForInfo io = new IOForInfo(CTInfo.DATA_PATH + "NewsText\\key.txt");
        try {
            io.setInfo(key);
        } catch (IOException e) {
            Log.err.print(NewsTextControl.class, "新闻获取密钥保存失败", e);
        }
    }
}
