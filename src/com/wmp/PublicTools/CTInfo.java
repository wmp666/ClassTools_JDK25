package com.wmp.PublicTools;

import com.wmp.Main;
import com.wmp.PublicTools.UITools.CTColor;
import com.wmp.PublicTools.UITools.CTFont;
import com.wmp.PublicTools.UITools.CTFontSizeStyle;
import com.wmp.PublicTools.appFileControl.IconControl;
import com.wmp.PublicTools.appFileControl.AudioControl;
import com.wmp.PublicTools.appFileControl.appInfoControl.AppInfo;
import com.wmp.PublicTools.appFileControl.appInfoControl.AppInfoControl;
import com.wmp.PublicTools.io.IOForInfo;
import com.wmp.PublicTools.printLog.Log;
import com.wmp.classTools.frame.MainWindow;
import com.wmp.classTools.infSet.panel.personalizationSets.control.PBasicInfo;
import com.wmp.classTools.infSet.panel.personalizationSets.control.PBasicInfoControl;
import com.wmp.classTools.infSet.panel.personalizationSets.control.PPanelInfo;
import com.wmp.classTools.infSet.panel.personalizationSets.control.PPanelInfoControl;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public class CTInfo {
    //禁用的组件和按钮
    public static final ArrayList<String> disPanelList = new ArrayList<>();
    public static final ArrayList<String> disButList = new ArrayList<>();
    //控件数据
    public static final int arch = 15;
    public static final int arcw = 15;
    public static double dpi = 1.0;
    public static boolean isButtonUseMainColor = false;
    //数据位置
    public static String DATA_PATH;
    public static String TEMP_PATH;
    public static String APP_INFO_PATH;
    //基础数据
    public static String appName = "班级工具";
    public static String author = "无名牌";
    public static String iconPath = "/image/icon/icon.png";
    //其他
    public static boolean isError = false;
    public static boolean canExit = true;
    public static boolean StartUpdate = true;

    public static String version = Main.version;

    public static AppInfo appInfo = new AppInfo(5, false);

    public static void  init() {

        if (MainWindow.mainWindow != null){
            Taskbar taskbar = Taskbar.getTaskbar();

            if (taskbar.isSupported(Taskbar.Feature.USER_ATTENTION_WINDOW)) {
                taskbar.requestWindowUserAttention(MainWindow.mainWindow);
            }

            if (taskbar.isSupported(Taskbar.Feature.PROGRESS_STATE_WINDOW)) {
                taskbar.setWindowProgressState(MainWindow.mainWindow, Taskbar.State.INDETERMINATE);
            }
        }

        dpi = 1.0;
        disButList.clear();
        disPanelList.clear();

        //加载基础目录
        String path = System.getenv("LOCALAPPDATA");
        String s = Main.getTheArgNextArg("BasicDataPath");
        if (s == null) {
            if (path != null && !path.isEmpty()){
                File file = new File(path, "\\ClassTools\\basicDataPath.txt");
                if (file.exists() && file.isFile()) {
                    try {
                        path = new File(Files.readString(file.toPath(), StandardCharsets.UTF_8)).getAbsolutePath();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }else path = s;

        APP_INFO_PATH = path + "\\ClassToolsAppFile\\";
        DATA_PATH = path + "\\ClassTools\\";
        TEMP_PATH = path + "\\ClassToolsTemp\\";

        if (!isError) {
            if (version.split("\\.").length < 5) iconPath = "/image/icon/icon.png";
            else iconPath = "/image/icon/icon_preview.png";
        } else iconPath = "/image/err/icon.png";

        initCTInfo();

        initCTBasicInfo();



        if (MainWindow.mainWindow != null){
            Taskbar taskbar = Taskbar.getTaskbar();

            if (taskbar.isSupported(Taskbar.Feature.PROGRESS_STATE_WINDOW)) {
                taskbar.setWindowProgressState(MainWindow.mainWindow, Taskbar.State.OFF);
            }
        }

    }

    private static void initCTBasicInfo() {

        appInfo = new AppInfoControl().getInfo();

        if (appInfo.joinInsiderProgram()){
            int[] versionArr = new int[5];
            String[] versionStr = version.split("\\.");
            for (int i = 0; i < versionStr.length; i++) {
                versionArr[i] = Integer.parseInt(versionStr[i]);
            }
            int[] result = Arrays.copyOf(versionArr, 5);
            version = String.format("%d.%d.%d.%d.%d", result[0], result[1], result[2], result[3], result[4]);

            Log.trayIcon.displayMessage("提示", "您加入了测试计划,可能遇到大量为未经验证的更新,请关注", TrayIcon.MessageType.WARNING);
        }

        //设置默认字体
        ColorUIResource textColorUIResource = new ColorUIResource(CTColor.textColor);
        ColorUIResource backColorUIResource = new ColorUIResource(CTColor.backColor);
        for(Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();){
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if(value instanceof ColorUIResource) {
                if (key.toString().endsWith("background"))
                    UIManager.put(key, backColorUIResource);
                else if (key.toString().endsWith("foreground"))
                    UIManager.put(key, textColorUIResource);
            }
        }

        IconControl.init();
        AudioControl.init();
        Log.initTrayIcon();
    }

    private static void initCTInfo() {

        PBasicInfo basicInfo = new PBasicInfoControl().getInfo();
        PPanelInfo panelInfo = new PPanelInfoControl().getInfo();

        //基础数据

        //颜色数据
        CTColor.setMainColor(basicInfo.mainColor());
        CTColor.setMainTheme(basicInfo.mainTheme());
        isButtonUseMainColor = basicInfo.buttonColor();
        //字体数据
        CTFont.setFontName(basicInfo.fontName());
        //界面数据
        disButList.addAll(List.of(basicInfo.disposeButton()));
        canExit = basicInfo.canExit();
        StartUpdate = basicInfo.startUpdate();
        if (basicInfo.isSaveLog()) {
            Log.isSaveLog(true);
        }

        //组件数据
        MainWindow.allPanelList.forEach(panel ->{
            panel.setIgnoreState(List.of(panelInfo.runInBackgroundList()).contains(panel.getID()));
        });
        disPanelList.addAll(List.of(panelInfo.disPanelList()));
        dpi = panelInfo.dpi();

    }
}
