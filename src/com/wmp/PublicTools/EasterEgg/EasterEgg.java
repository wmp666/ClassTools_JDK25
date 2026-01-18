package com.wmp.PublicTools.EasterEgg;

import com.wmp.Main;
import com.wmp.PublicTools.CTInfo;
import com.wmp.PublicTools.DateTools;
import com.wmp.PublicTools.UITools.GetIcon;
import com.wmp.PublicTools.appFileControl.IconControl;
import com.wmp.PublicTools.io.GetPath;
import com.wmp.PublicTools.io.IOForInfo;
import com.wmp.PublicTools.io.ResourceLocalizer;
import com.wmp.PublicTools.printLog.Log;
import com.wmp.PublicTools.videoView.MediaPlayer;
import com.wmp.PublicTools.web.GetWebInf;
import com.wmp.classTools.CTComponent.CTOptionPane;
import com.wmp.classTools.SwingRun;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class EasterEgg {

    public static final int STYLE_EE_VIDEO = 0;
    public static final int STYLE_EE_MUSIC = 1;
    public static final int STYLE_EE_OTHER = 2;

    public static EasterEggModeMap getEasterEggItem() {
        EasterEggModeMap errMode = new EasterEggModeMap("999.999.999", "银狼", "班级病毒",
                "/image/err/icon.png", "骇客已入侵", true,
                new Color(246, 250, 255), new Color(0x29A8E3), new Color(0x29A8E3), "err",
                false, () -> {
            Log.info.loading.updateDialog("窗口加载", "骇客已强制介入加载过程");
            Log.err.systemPrint(SwingRun.class, "程序加载出错!", new Exception("加载异常"));

            Log.info.loading.showDialog("修复", "正在启动修复程序...");

            try {
                Log.info.loading.updateDialog("修复", "开始扫描程序文件...");
                {
                    AtomicInteger count = new AtomicInteger(0);
                    Path appPath = Path.of(GetPath.getAppPath(GetPath.APPLICATION_PATH));
                    long fileCount = Files.walk(appPath)
                            .count();

                    if (fileCount > 0) {
                        Files.walk(appPath)
                                .sorted()
                                .map(Path::toFile)
                                .forEach(file -> {
                                    int currentCount = count.incrementAndGet();
                                    double percentage = (currentCount * 100.0) / fileCount;
                                    int progress = (int) Math.round(percentage);
                                    Log.info.loading.updateDialog("修复",
                                            String.format("开始扫描程序文件%.2f%%", percentage),
                                            progress);
                                });
                    }
                }

                Log.info.loading.updateDialog("修复", "正在扫描数据文件...");
                {
                    AtomicInteger count = new AtomicInteger(0);
                    Path dataPath = Path.of(CTInfo.TEMP_PATH).getParent();
                    long fileCount = Files.walk(dataPath)
                            .count();

                    if (fileCount > 0) {
                        Files.walk(dataPath)
                                .sorted()
                                .map(Path::toFile)
                                .forEach(file -> {
                                    int currentCount = count.incrementAndGet();
                                    double percentage = (currentCount * 100.0) / fileCount;
                                    int progress = (int) Math.round(percentage);
                                    Log.info.loading.updateDialog("修复",
                                            String.format("开始扫描数据文件%.2f%%", percentage),
                                            progress);
                                });
                    }
                }
            } catch (Exception _) {
            }

            Log.info.loading.updateDialog("修复", "正在修复文件...", -1);
            {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException _) {

                }
                Log.info.loading.updateDialog("修复", "修复出错!");
                Log.err.systemPrint(SwingRun.class, "修复出错", new Exception("Silver Wolf强制截停修复进程"));
            }

            Log.info.loading.updateDialog("修复", "修复出错,正在准备关闭程序", -1);
            Log.err.systemPrint(SwingRun.class, "骇客已入侵", new Exception("关闭程序时出现错误,无法修复"));

            Log.info.loading.showDialog("骇客已入侵", "正在修改修复程序");
            {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException _) {

                }
                Log.info.loading.updateDialog("骇客已入侵", "已修改修复程序");
                Log.info.loading.closeDialog("骇客已入侵");
            }

            Log.info.loading.updateDialog("修复", "修复成功!正在启动程序", -1);
            {
                for (int i = 0; i < 100; i++) {
                    Log.info.loading.updateDialog("修复", i);
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException _) {

                    }
                }
            }

            Log.info.loading.closeDialog("修复");
            Log.info.systemPrint("骇客已入侵", "这次能让我玩得开心点么？");
        });


        //强制启动骇客入侵模式
        if (Main.isHasTheArg("CTInfo:isError")) return errMode;

        if (Main.isHasTheArg("EasterEgg:notShow")) return CTInfo.easterEggModeMap;

        //铭记

        //祖国万岁
        if (DateTools.dayIsNow("09-18") ||
                DateTools.dayIsNow("10-01") ||
                DateTools.dayIsNow("05-01") ||
                DateTools.dayIsNow("12-26")) {
            return new EasterEggModeMap("999.10.01", "中国人民", "中华人民共和国",
                    "/image/icon/icon_red.png", "祖国万岁", true,
                    new Color(255, 214, 214), Color.RED, Color.RED, "light",
                    true, ()->{
                CTOptionPane.showFullScreenMessageDialog("祖国万岁", "中华人民共和国万岁!", 60, 1);
            });
        }

        //骇客入侵
        {
            boolean b = DateTools.dayIsNow("04-07");
            if (!b) {
                if (DateTools.dayIsNow("04-25")) {//崩铁
                    Random r = new Random();
                    if (r.nextInt(5) == 0) {
                        return errMode;
                    }

                }
                Random r = new Random();
                if (r.nextInt(20) == 0) {
                    return errMode;
                }
            }
        }

        //生日
        {
            // 茜特菈莉
            if (DateTools.dayIsNow("01-20")) {
                return new EasterEggModeMap("999.01.20", "茜特菈莉", "烟谜主",
                        "/image/err/xtll.png", "茜特菈莉", true,
                        new Color(217, 208, 229), new Color(0x9A93DD), new Color(0x6F65C7), "light",
                        true, () -> {
                    CTOptionPane.showFullScreenMessageDialog(CTInfo.appName, "今天是...?", 3, 1);
                });
            }
            //温迪
            if (DateTools.dayIsNow("06-16")) {
                return new EasterEggModeMap("999.06.16", "温迪", "蒙德",
                        "/image/err/xtll.png", "温迪", true,
                        new Color(230, 255, 221), new Color(0x05E666), new Color(0x05E666), "light",
                        true, () -> {
                    CTOptionPane.showFullScreenMessageDialog(CTInfo.appName, "今天是...?", 3, 1);
                });
            }
            //散兵
            if (DateTools.dayIsNow("01-03")) {
                return new EasterEggModeMap("999.01.03", "散兵", "稻妻",
                        "/image/err/sanbing.png", "散兵", true,
                        new Color(230, 255, 221), new Color(0x29A5E3), new Color(0x29A5E3), "light",
                        true, () -> {
                    CTOptionPane.showFullScreenMessageDialog(CTInfo.appName, "今天是...?", 3, 1);
                });
            }
            //哥伦比娅
            if (DateTools.dayIsNow("01-14")) {
                return new EasterEggModeMap("999.01.14", "哥伦比娅•希泊塞莱尼娅", "新月",
                        "/image/err/yueshen.jpg", "空月", true,
                        new Color(230, 255, 221), new Color(0x29A5E3), new Color(0x29A5E3), "light",
                        true, () -> {
                    CTOptionPane.showFullScreenMessageDialog(CTInfo.appName, "你可以叫我哥伦比娅，也可以叫我库塔尔、月神大人...选你喜欢的吧。我习惯了有很多名字的日子。 若你需要的话，我会给予你月亮的赐福。", 3, 1);
                });
            }
            //这位是？
            if (DateTools.dayIsNow("10-11")) {
                return new EasterEggModeMap("999.10.11", "ᝰꫛ", "ᝰꫛ",
                        "/image/err/lcl.jpg", "ᝰꫛ", true,
                        new Color(230, 255, 221), new Color(0x29A5E3), new Color(0x29A5E3), "light",
                        true, () -> {
                    CTOptionPane.showFullScreenMessageDialog(CTInfo.appName, "个签：\n风很温柔 花很浪漫 你很特别 我喜欢你.", 3, 1);
                });
            }
        }


        //一些较特殊的纪念日
        if( DateTools.dayIsNow("09-28") ||//原神周年庆
                DateTools.dayIsNow("lunar9-17") ||//author birthday
                DateTools.dayIsNow("09-03") ||//mc
                DateTools.dayIsNow("04-25")) {//崩铁
            return new EasterEggModeMap("999.999.999", "彩蛋", "班级■■",
                    "/image/icon/icon_red.png", "欸嘿", true,
                    new Color(230, 255, 221), new Color(0x05E666), new Color(0x05E666), "light",
                    true, ()->{
                CTOptionPane.showFullScreenMessageDialog("欸嘿", "欸嘿", 3, 1);
            });
        }

        //新年
        if (DateTools.dayIsNow("12-31") ||
                DateTools.dayIsNow("01-01") ||
                DateTools.dayIsNow("01-02") ||
        (DateTools.getRemainderDay("lunar01-15") <= 30)) {

            return new EasterEggModeMap("999.01.01", "刘德华", "恭喜发财",
                    "/image/icon/icon_red.png", "恭喜发财", true,
                    new Color(248, 217, 217), Color.RED, Color.RED, "light",
                    true, ()->{
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(URI.create("https://www.bilibili.com/video/BV1ad4y1V7wb"));
                    } catch (IOException _) {
                        Log.info.print(null, EasterEgg.class.toString(), "浏览器打开失败");
                    }
                }
            });
        }

        //愚人节
        boolean b = DateTools.dayIsNow("04-01");
        if (b) {
            return new EasterEggModeMap("999.999.999", "彩蛋", "班级■■",
                    "/image/err/icon.png", "欸嘿", true,
                    new Color(230, 255, 221), new Color(0x05E666), new Color(0x05E666), "light",
                    true, ()->{
                CTOptionPane.showFullScreenMessageDialog("欸嘿", "欸嘿", 3, 1);
            });
        }

        return CTInfo.easterEggModeMap;
    }

    public static void getPin() {


        String style = Log.info.showChooseDialog(null, "祈愿", "请输入选择彩蛋格式\n注:\"其他\"指不是常规格式(MP3, MP4)的文件", "视频", "音乐", "其他");

        new Thread(() -> Log.info.print("彩蛋", "正在获取数据,稍安勿躁...")).start();
        try {

            AtomicReference<JSONArray> info = new AtomicReference<>(new JSONArray());

            //name, key
            HashMap<String, String> keyMap = new HashMap<>();
            //name, URL
            HashMap<String, String> musicMap = new HashMap<>();
            HashMap<String, String> videoMap = new HashMap<>();
            HashMap<String, String> otherMap = new HashMap<>();

            //获取彩蛋列表
            String webInf = GetWebInf.getWebInf("https://api.github.com/repos/wmp666/ClassTools/releases/tags/0.0.1");
            JSONObject jsonObject = new JSONObject(webInf);

            JSONArray assets = jsonObject.getJSONArray("assets");
            assets.forEach(asset -> {
                if (asset instanceof JSONObject jsonObject1) {
                    String name = jsonObject1.getString("name");
                    String browser_download_url = jsonObject1.getString("browser_download_url");

                    keyMap.put(name, name);

                    if (name.endsWith(".mp3")) {
                        musicMap.put(name, browser_download_url);
                    } else if (name.endsWith(".mp4")) {
                        videoMap.put(name, browser_download_url);
                    } else if (name.equals("EasterEggInfo.json")) {
                        try {
                            String s = GetWebInf.getWebInf(browser_download_url);
                            info.set(new JSONArray(s));
                        } catch (Exception e) {
                            Log.err.print(EasterEgg.class, "数据获取失败", e);
                        }
                    } else {
                        otherMap.put(name, browser_download_url);
                    }
                }
            });

            //根据EasterEgg.json中的数据,修改彩蛋名
            info.get().forEach(object -> {
                if (object instanceof JSONObject jsonObject2) {
                    String key = jsonObject2.getString("key");
                    String name = jsonObject2.getString("name");

                    keyMap.remove(key);
                    keyMap.put(name, key);

                    if (key.endsWith(".mp3")) {
                        String s = musicMap.get(key);
                        musicMap.remove(key);
                        musicMap.put(name, s);
                    } else if (key.endsWith(".mp4")) {
                        String s = videoMap.get(key);
                        videoMap.remove(key);
                        videoMap.put(name, s);
                    } else {
                        String s = otherMap.get(key);
                        otherMap.remove(key);
                        otherMap.put(name, s);
                    }
                }
            });

            int styleInt = STYLE_EE_OTHER;
            String name = "";
            String url = "";
            switch (style) {
                case "视频" -> {
                    String[] names = videoMap.keySet().toArray(new String[0]);
                    String s = Log.info.showChooseDialog(null, "祈愿", "请选择彩蛋", names);
                    name = keyMap.get(s);
                    url = videoMap.get(s);
                    styleInt = STYLE_EE_VIDEO;
                }
                case "音乐" -> {
                    String[] names = musicMap.keySet().toArray(new String[0]);
                    String s = Log.info.showChooseDialog(null, "祈愿", "请选择彩蛋", names);
                    name = keyMap.get(s);
                    url = musicMap.get(s);
                    styleInt = STYLE_EE_MUSIC;
                }
                case "其他" -> {
                    String[] names = otherMap.keySet().toArray(new String[0]);
                    String s = Log.info.showChooseDialog(null, "祈愿", "请选择彩蛋", names);
                    name = keyMap.get(s);
                    url = otherMap.get(s);
                    styleInt = STYLE_EE_OTHER;
                }

            }
            showEasterEgg(styleInt, name, url);


        } catch (Exception e) {
            Log.err.print(null, EasterEgg.class, "获取彩蛋失败", e);
        }
    }

    public static void showEasterEgg(int style, String name, String url) {
        Log.info.print("EasterEgg-显示", "正在准备...");


        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                // 异步下载（在后台线程执行）
                Log.info.print("EasterEgg-下载", "正在下载...");

                Log.info.print("EasterEgg-下载", "下载链接: " + url);

                if (style == STYLE_EE_VIDEO)
                    ResourceLocalizer.copyWebFile(CTInfo.TEMP_PATH + "EasterEgg\\video\\", url, name);
                else if (style == STYLE_EE_MUSIC)
                    ResourceLocalizer.copyWebFile(CTInfo.TEMP_PATH + "EasterEgg\\music\\", url, name);
                else if (style == STYLE_EE_OTHER)
                    ResourceLocalizer.copyWebFile(CTInfo.TEMP_PATH + "EasterEgg\\other\\", url, name);
                return null;
            }

            @Override
            protected void done() {
                // 下载完成后在EDT线程执行
                try {

                    get(); // 获取执行结果（可捕获异常）


                    if (style == STYLE_EE_MUSIC) {
                        String path = CTInfo.TEMP_PATH + "EasterEgg\\music\\" + name;
                        MediaPlayer.playLocalMusic(path);
                    } else if (style == STYLE_EE_VIDEO) {
                        String path = CTInfo.TEMP_PATH + "EasterEgg\\video\\" + name;
                        MediaPlayer.playVideo(path);
                    } else if (style == STYLE_EE_OTHER) {
                        String path = CTInfo.TEMP_PATH + "EasterEgg\\other\\" + name;
                        MediaPlayer.playOther(path);
                    }


                } catch (Exception e) {
                    Log.err.print(null, EasterEgg.class, "下载失败", e);
                }
            }
        }.execute();


    }


    public static String getText(EETextStyle style) {
        String[] easterEggList = getAllText();
        String s = easterEggList[new Random().nextInt(easterEggList.length)];
        switch (style) {
            case DEFAULT -> {
                return s;
            }
            case HTML -> {
                String s1 = "<html>" + s.replace("\\n", "<br>") + "</html>";
                Log.info.print("获取彩蛋文字", s1);
                return s1;
            }
        }
        return "err";
    }

    public static String[] getAllText() {

        List<String> list = new ArrayList<>();
        String[] info = IOForInfo.getInfo(EasterEgg.class.getResource("EasterEgg.txt"));
        for (String s : info) {
            list.add(s);
        }
        return list.toArray(new String[0]);

    }

    public static void errorAction() {
        Log.info.print("EasterEgg", "你没有权限!!!");

        CTOptionPane.showMessageDialog(null, "doge", "你没有权限!!!", GetIcon.getIcon("彩蛋.刻律德菈", IconControl.COLOR_DEFAULT, 100, 100), CTOptionPane.ERROR_MESSAGE, true);
    }

    public static void showHolidayBlessings(int style) {

        if (Main.isHasTheArg("EasterEgg:notShow")) return;

        Log.info.print("EasterEgg", "搜索今日是否需要祝福");

        AtomicBoolean b = new AtomicBoolean(false);
        try {
            //获取文件
            String jsonArrayStr = IOForInfo.getInfos(Objects.requireNonNull(EasterEgg.class.getResource("HBText.json")));
            JSONArray jsonArray = new JSONArray(jsonArrayStr);

            //获取时间

            jsonArray.forEach(jsonObject -> {


                if (jsonObject instanceof JSONObject jsonObject1) {

                    String date1 = jsonObject1.getString("date");
                    if (DateTools.dayIsNow(date1)) {

                        b.set(true);
                        Main.argsList.add("-StartUpdate:false");

                        String text = jsonObject1.getString("text");
                        String title = jsonObject1.getString("title");

                        CTOptionPane.showFullScreenMessageDialog(title, text, 5, 5);

                    }

                } else {
                    Log.err.print(EasterEgg.class, "获取彩蛋文件数据异常: \n" + jsonObject);
                }
            });


        } catch (Exception e) {
            Log.err.print(EasterEgg.class, "获取彩蛋文字失败", e);
        }
        if (style == 1 && !b.get()) Log.info.message(null, "EasterEgg", "今日无彩蛋");
    }
}
