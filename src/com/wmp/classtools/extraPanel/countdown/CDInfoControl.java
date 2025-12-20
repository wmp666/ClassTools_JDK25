package com.wmp.classTools.extraPanel.countdown;

import com.wmp.PublicTools.CTInfo;
import com.wmp.PublicTools.appFileControl.CTInfoControl;
import com.wmp.PublicTools.io.IOForInfo;
import com.wmp.PublicTools.printLog.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 倒计时信息控制类,若没有相关数据,将返回<code>null</code>
 */
public class CDInfoControl extends CTInfoControl<CountDownInfos> {
/*
    private CDInfo getCDInfo() {

        try {
            IOForInfo io = new IOForInfo(path);
            String s = io.getInfos();
            if (!s.equals("err")) {
                if (s.startsWith("{")) {
                    JSONObject json = new JSONObject(s);
                    return new CDInfo(json.getString("title"), json.getString("targetTime"));
                } else {
                    JSONArray jsonArray = new JSONArray(s);

                    AtomicReference<CDInfo> resultInfo = new AtomicReference<>(new CDInfo("数据出错", "2030.01.01 00:00:00"));
                    //CDInfo resultInfo = new CDInfo("数据出错", "2030.01.01 00:00:00");
                    AtomicLong remainderTime = new AtomicLong();

                    jsonArray.forEach(o -> {
                        if (o instanceof JSONObject json) {
                            CDInfo info = new CDInfo(json.getString("title"), json.getString("targetTime"));
                            long tempRemainderTime = DateTools.getRemainderTime(info.targetTime, "yyyy.MM.dd HH:mm:ss");

                            if (resultInfo.get().title.equals("数据出错")) {
                                resultInfo.set(info);
                                remainderTime.set(tempRemainderTime);
                                return;
                            }

                            if (tempRemainderTime > 0) {

                                if (remainderTime.get() > tempRemainderTime) {
                                    resultInfo.set(info);
                                    remainderTime.set(tempRemainderTime);
                                }
                            }
                        }
                    });
                    return resultInfo.get();
                }


            } else return new CDInfo("数据出错", "2030.01.01 00:00:00");
        } catch (Exception e) {
            Log.err.print(CDInfoControl.class, "获取倒计时信息失败", e);
        }
        return new CDInfo("数据出错", "2030.01.01 00:00:00");
    }



    private CDInfo[] getCDInfos() {
        try {
            IOForInfo io = new IOForInfo(path);
            String s = io.getInfos();
            if (!s.equals("err")) {
                if (s.startsWith("{")) {
                    JSONObject json = new JSONObject(s);
                    return new CDInfo[]{new CDInfo(json.getString("title"), json.getString("targetTime"))};
                } else {
                    JSONArray jsonArray = new JSONArray(s);
                    ArrayList<CDInfo> resultInfo = new ArrayList<>();

                    jsonArray.forEach(o -> {
                        if (o instanceof JSONObject json) {
                            CDInfo info = new CDInfo(json.getString("title"), json.getString("targetTime"));
                            resultInfo.add(info);
                        }
                    });
                    return resultInfo.toArray(new CDInfo[0]);
                }


            } else return new CDInfo[]{new CDInfo("数据出错", "2030.01.01 00:00:00")};
        } catch (Exception e) {
            Log.err.print(CDInfoControl.class, "获取倒计时信息失败", e);
        }
        return new CDInfo[]{new CDInfo("数据出错", "2030.01.01 00:00:00")};
    }
    */

    @Override
    public File getInfoBasicFile() {
        return new File(CTInfo.DATA_PATH, "CountDown.json");
    }

    @Override
    public void setInfo(CountDownInfos countDownInfos) {
        try {
            if (countDownInfos == null) return;
            JSONArray jsonArray = new JSONArray();
            for (CountDownInfo info : countDownInfos.list()) {
                if (info == null) continue;

                JSONObject json = new JSONObject();
                json.put("title", info.title());
                json.put("targetTime", info.targetTime());

                jsonArray.put(json);
            }
            IOForInfo io = new IOForInfo(getInfoBasicFile());

            io.setInfo(jsonArray.toString(4));
        } catch (IOException e) {
            Log.err.print(getClass(), "保存倒计时信息失败", e);
        }
    }
    @Override
    public CountDownInfos refresh() {
        try {
            IOForInfo io = new IOForInfo(getInfoBasicFile());
            String s = io.getInfos();
            if (!s.equals("err")) {
                if (s.startsWith("{")) {
                    JSONObject json = new JSONObject(s);
                    ArrayList<CountDownInfo> resultInfo = new ArrayList<>();
                    resultInfo.add(new CountDownInfo(json.getString("title"), json.getString("targetTime")));
                    return new CountDownInfos(resultInfo);
                } else {
                    JSONArray jsonArray = new JSONArray(s);
                    ArrayList<CountDownInfo> resultInfo = new ArrayList<>();
                    jsonArray.forEach(o -> {
                        if (o instanceof JSONObject json) {
                            resultInfo.add(new CountDownInfo(json.getString("title"), json.getString("targetTime")));
                        }
                    });
                    return new CountDownInfos(resultInfo);
                }
            }
        } catch (IOException e) {
            Log.err.systemPrint(getClass(), "获取倒计时信息失败", e);
        }
        return null;
    }
}
