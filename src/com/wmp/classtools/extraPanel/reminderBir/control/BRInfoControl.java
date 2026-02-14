package com.wmp.classTools.extraPanel.reminderBir.control;

import com.wmp.publicTools.CTInfo;
import com.wmp.publicTools.appFileControl.CTInfoControl;
import com.wmp.publicTools.io.IOForInfo;
import com.wmp.publicTools.printLog.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

public class BRInfoControl extends CTInfoControl<BRInfo[]> {
    @Override
    public File getInfoBasicFile() {
        return new File(CTInfo.DATA_PATH, "birthday.json");
    }

    @Override
    public void setInfo(BRInfo[] brInfos) {
        try {
            JSONArray jsonArray = new JSONArray();
            for (BRInfo info : brInfos) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", info.name());
                jsonObject.put("birthday", info.birthday());
                jsonArray.put(jsonObject);
            }
            IOForInfo ioForInfo = new IOForInfo(getInfoBasicFile());
            ioForInfo.setInfo(jsonArray.toString(4));
        } catch (Exception e) {
            Log.err.print(getClass(), "保存生日信息失败", e);
        }
    }

    @Override
    protected BRInfo[] refreshInfo() {
        if (getInfoBasicFile().exists()) {
            try {
                String infos = IOForInfo.getInfos(getInfoBasicFile().toURI().toURL());
                if (infos != null) {
                    JSONArray jsonArray = new JSONArray(infos);
                    BRInfo[] brInfos = new BRInfo[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        brInfos[i] = new BRInfo(jsonObject.getString("name"),
                                jsonObject.getString("birthday"));
                    }
                    return brInfos;
                }
            } catch (Exception e) {
                Log.err.print(getClass(), "获取生日信息失败", e);
            }
        }
        return new BRInfo[]{
                new BRInfo("无", "00-00"),
        };
    }
}
