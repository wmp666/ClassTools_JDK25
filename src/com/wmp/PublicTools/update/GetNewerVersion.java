package com.wmp.PublicTools.update;

import com.wmp.Main;
import com.wmp.PublicTools.CTInfo;
import com.wmp.PublicTools.io.DownloadURLFile;
import com.wmp.PublicTools.io.GetPath;
import com.wmp.PublicTools.io.IOForInfo;
import com.wmp.PublicTools.printLog.Log;
import com.wmp.PublicTools.web.GetWebInf;
import com.wmp.PublicTools.web.SslUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class GetNewerVersion {

    private static final int NEW_VERSION = 1;
    private static final int TEST_VERSION = 2;
    private static final String NewVerFileUrl = "https://api.github.com/repos/wmp666/ClassTools_JDK25/releases/latest";
    public static int newerVersion = 1;
    public static int importUpdate = 2;
    private static JPanel view;
    //https://github.com/wmp666/ClassTools_JDK25/releases/download/1.6.4/ClassTools.jar

    /**
     *
     * @param updateMode 更新模式
     * @return [版本,下载地址,sha265,更新提示]
     */
    private static String[] getLatestVersion(int updateMode) {
        try {
            String[] info = new String[4];
            if (updateMode == NEW_VERSION) {
                // 获取原始JSON响应
                String json = GetWebInf.getWebInf(NewVerFileUrl);

                // 使用JSONObject解析
                JSONObject release = new JSONObject(json);
                info[0] = release.getString("tag_name");
                info[3] = release.getString("body").replace("\\r\\n", "\n");

                // 获取准确下载地址
                JSONArray assets = release.getJSONArray("assets");
                for (int i = 0; i < assets.length(); i++) {
                    JSONObject asset = assets.getJSONObject(i);
                    if (asset.getString("name").endsWith(".jar")) {
                        info[1] = asset.getString("browser_download_url");
                        info[2] = asset.getString("digest").split(":")[1];
                        break;
                    }
                }
            } else {
                // 获取原始JSON响应
                String json = GetWebInf.getWebInf("https://api.github.com/repos/wmp666/ClassTools_JDK25/releases");

                AtomicReference<String> newBate = new AtomicReference<>("0.0.0");
                AtomicReference<JSONObject> newRelease = new AtomicReference<>();
                // 使用JSONObject解析
                JSONArray releases = new JSONArray(json);
                //获取最新测试版
                releases.forEach(release -> {
                    JSONObject JSONRelease = (JSONObject) release;
                    if (JSONRelease.getBoolean("prerelease")) {
                        if (isNewerVersion(JSONRelease.getString("tag_name"), newBate.get()) != 0) {
                            newBate.set(JSONRelease.getString("tag_name"));
                            newRelease.set(JSONRelease);
                        }
                    }
                });

                info[0] = newBate.get();
                info[3] = newRelease.get().getString("body").replace("\\r\\n", "\n");

                // 获取准确下载地址
                JSONArray assets = newRelease.get().getJSONArray("assets");
                for (int i = 0; i < assets.length(); i++) {
                    JSONObject asset = assets.getJSONObject(i);
                    if (asset.getString("name").endsWith(".jar")) {
                        info[2] = asset.getString("digest").split(":")[1];
                        info[1] = asset.getString("browser_download_url");
                        break;
                    }
                }

            }
            if (info[1].equals("null")) {
                Log.err.print(null, GetNewerVersion.class, "无法获取下载地址");
            } else {
                Log.info.print(null, "获取新版本", "获取下载地址成功");
            }
            return info;
        } catch (Exception e) {
            Log.err.systemPrint(GetNewerVersion.class, "版本获取失败", e);
        }
        return new String[]{"", "", "", ""};
    }

    public static void checkForUpdate(Window dialog, JPanel panel, boolean showMessage) {
        checkForUpdate(dialog, panel, showMessage, true);
    }

    public static void checkForUpdate(Window dialog, JPanel panel, boolean showMessage, boolean inquireUpdateWay) {

        if (Main.isHasTheArg("screenProduct:show")) {
            Log.err.print(null, GetNewerVersion.class, "屏保状态,无法更新");
            return;
        }

        int updateMode;

        if (inquireUpdateWay) {
            String s = Log.info.showChooseDialog(dialog, "检查更新", "选择更新版本", "最新版", "测试版");
            //updateMode = s.equals() ? NEW_VERSION : TEST_VERSION;
            if (s.equals("最新版")) updateMode = NEW_VERSION;
            else if (s.equals("测试版")) updateMode = TEST_VERSION;
            else {
                Log.err.print(null, GetNewerVersion.class, "未设置更新方式");
                return;
            }
        } else {
            updateMode = NEW_VERSION;
        }


        if (panel != null) {
            panel.removeAll();
        }

        new SwingWorker<Void, Void>() {
            String[] latestInfo;

            protected Void doInBackground() {
                String[] temp = getLatestVersion(NEW_VERSION);
                if (CTInfo.appInfo.joinInsiderProgram() || updateMode == TEST_VERSION){
                        String[] testLatestInfo = getLatestVersion(TEST_VERSION);
                        if (isNewerVersion(testLatestInfo[0], temp[0]) != 0)
                            latestInfo = testLatestInfo;
                        else latestInfo = temp;
                }else latestInfo = temp;
                return null;
            }

            protected void done() {
                if (latestInfo == null) {
                    return;
                }
                int i = isNewerVersion(latestInfo[0], CTInfo.version);

                Thread updateThread = new Thread(() -> {
                    Log.warn.message(dialog, "更新至 " + latestInfo[0], "即将开始更新, 无论更新是否完成都将关闭程序, 没有提醒");

                    IOForInfo.deleteDirectoryRecursively(Path.of(CTInfo.TEMP_PATH + "UpdateFile\\"));

                    DownloadURLFile.downloadWebFile(dialog, panel, latestInfo[1], CTInfo.TEMP_PATH + "UpdateFile\\", latestInfo[2]);

                    try {
                        IOForInfo.copyFile(Path.of(CTInfo.TEMP_PATH, "UpdateFile", "ClassTools.jar"), Path.of(GetPath.getAppPath(GetPath.SOURCE_FILE_PATH), "ClassTools.jar"));
                    } catch (Exception e) {
                        Log.err.print(dialog, GetNewerVersion.class, "更新文件失败", e);
                    }

                    System.exit(0);
                });

                if (updateMode == NEW_VERSION) {
                    if (i == 1) {
                        Log.info.print("发现新版本", "发现新版本 " + latestInfo[0]);
                        int result = Log.info.showChooseDialog(dialog, "发现新版本",
                                "发现新版本 " + latestInfo[0] + "，是否下载？\n" + latestInfo[3]);


                        if (result == JOptionPane.YES_OPTION) {
                            updateThread.start();

                        }
                    } else {
                        if (showMessage) {
                            Log.info.message(dialog, "获取新版本", "当前已是最新版本");

                        } else {
                            Log.info.print("获取新版本", "当前已是最新版本");
                        }

                    }
                } else {
                    if (i != 0) {
                        int result = Log.info.showChooseDialog(dialog, "发现测试版本",
                                "发现测试版本 " + latestInfo[0] + "，是否下载？\n" + latestInfo[3]);


                        if (result == JOptionPane.YES_OPTION) {
                            updateThread.start();

                        }
                    } else {
                        if (showMessage) {
                            Log.info.message(dialog, "获取测试版本", "无最新测试版本");

                        } else {
                            Log.info.print("获取测试版本", "无最新测试版本");
                        }

                    }
                }


            }
        }.execute();// 开始执行异步任务
    }

    /**
     * 比较版本号
     *
     * @param remote 新版本号
     * @param local  旧版本号
     * @return 0:已是最新，1:新版本，2:重要更新
     */
    public static int isNewerVersion(String remote, String local) {
        // 实现版本号比较逻辑（需根据你的版本号格式调整）
        String[] remoteParts = remote.split("\\.");
        String[] localParts = local.split("\\.");
        int maxSize = Math.max(remoteParts.length, localParts.length);
        remoteParts = Arrays.copyOf(remoteParts, maxSize);
        localParts = Arrays.copyOf(localParts, maxSize);

        for (int i = 0; i < maxSize; i++) {
            int remotePart = Integer.parseInt(remoteParts[i] == null?"0":remoteParts[i]);
            int localPart = Integer.parseInt(localParts[i] == null?"0":localParts[i]);
            if (remotePart > localPart) {//有最新版
                return newerVersion;
            } else if (remotePart < localPart) {
                return 0;
            }
        }
        return 0;
    }


    public static void getSource(Window dialog, JPanel panel) {
        if (panel != null) {
            view = panel;
            view.removeAll();
        }


        new SwingWorker<Void, Void>() {
            String sourceURL;

            protected Void doInBackground() throws Exception {
                sourceURL = getSourceURL();
                return null;
            }

            protected void done() {
                if (sourceURL == null) {
                    Log.err.print(dialog, GetNewerVersion.class, "无法获取下载地址");
                    return;
                }

                int result = Log.info.showChooseDialog(dialog, "发现新版本", "检测到源代码文件，是否下载？");

                if (result == JOptionPane.YES_OPTION) {
                    try {
                        downloadSource(sourceURL);
                    } catch (Exception e) {
                        Log.err.print(dialog, GetNewerVersion.class, "下载失败", e);
                    }
                }
            }
        }.execute();
    }

    private static String getSourceURL() {
        SslUtils.ignoreSsl();
        try {
            // 获取原始JSON响应
            String json = Jsoup.connect(NewVerFileUrl)
                    .header("Accept", "application/vnd.github.v3+json")
                    .timeout(10000)
                    .ignoreContentType(true)
                    .execute()
                    .body();

            // 使用JSONObject解析
            JSONObject release = new JSONObject(json);

            // 获取准确下载地址


            return release.getString("zipball_url");
        } catch (Exception e) {
            Log.err.print(GetNewerVersion.class, "信息解析失败", e);
        }
        return null;
    }

    private static void downloadSource(String downloadUrl) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI(downloadUrl));
    }


}
