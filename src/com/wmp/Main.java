package com.wmp;

import com.formdev.flatlaf.FlatLightLaf;
import com.wmp.PublicTools.CTInfo;
import com.wmp.PublicTools.EasterEgg.EasterEgg;
import com.wmp.PublicTools.StartupParameters;
import com.wmp.PublicTools.UITools.CTColor;
import com.wmp.PublicTools.UITools.GetIcon;
import com.wmp.PublicTools.printLog.Log;
import com.wmp.classTools.CTComponent.CTProgressBar.ModernLoadingDialog;
import com.wmp.classTools.SwingRun;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

public class Main {
    /**
     * a.b.c.d.e 例:1.5.3.1.1<br>
     * a:主版本号<br>
     * b:功能更新版本号<br>
     * c:修订版本号/小功能更新<br>
     * d:只修复的问题,问题较少<br>
     * e:测试版本号
     */
    public static final String version = "2.0.4";

    private static final TreeMap<String, StartupParameters> allArgs = new TreeMap<>();
    public static ArrayList<String> argsList = new ArrayList<>();

    static {
        //加载基础目录

        allArgs.put("StartUpdate:false", StartupParameters.creative("-StartUpdate:false", "/StartUpdate:false"));
        allArgs.put("screenProduct:show", StartupParameters.creative("/s", "-s"));
        allArgs.put("screenProduct:view", StartupParameters.creative("/p", "-p"));

        allArgs.put("CTInfo:isError", StartupParameters.creative("/CTInfo:error", "-CTInfo:error"));
        allArgs.put("BasicDataPath", StartupParameters.creative("/BasicDataPath", "-BasicDataPath"));
        allArgs.put("EasterEgg:notShow", StartupParameters.creative("/EasterEgg:notShow", "-EasterEgg:notShow"));
    }

    public static void main(String[] args) {
        System.out.println("版本：" +  version);
        if (args.length > 0) {
            argsList = new ArrayList<>(Arrays.asList(args));
            System.out.println("使用的启动参数:" + Arrays.toString(args));
        }

        FlatLightLaf.setup();

        if (version.split("\\.").length >= 5) {
            ImageIcon imageIcon = new ImageIcon(Main.class.getResource("/image/icon/icon_preview.png"));
            imageIcon.setImage(imageIcon.getImage().getScaledInstance(70,70, Image.SCALE_SMOOTH));
            JOptionPane.showMessageDialog(null, "当前为测试版,可能不稳定", "班级工具", JOptionPane.INFORMATION_MESSAGE,
                    imageIcon);
        }

        ModernLoadingDialog wait = new ModernLoadingDialog(null);
        wait.setAlwaysOnTop(true);
        wait.getLoader().startAnimation();
        wait.setIndeterminate(true);
        SwingUtilities.invokeLater(()->wait.setVisible(true));

        CTInfo.init();

        try {
            Log.info.loading.showDialog("程序加载", "正在启动...");
        } catch (Exception _) {

        }



        boolean b = false;
        boolean startUpdate = true;
        try {
            //GetSetsJSON setsJSON = new GetSetsJSON();

            b = EasterEgg.getEasterEggItem(EasterEgg.STYLE_IMPORT_DAY);

            startUpdate = CTInfo.StartUpdate;

        } catch (Exception e) {
            Log.info.loading.closeDialog("程序加载");
            wait.setVisible(false);
            wait.getLoader().stopAnimation();
            Log.err.print(Main.class, "初始化失败", e);
            Log.showLogDialog(true);
        }

        CTInfo.isError = EasterEgg.getEasterEggItem(EasterEgg.STYLE_ERROR);
        if (CTInfo.isError) {

            CTInfo.version = "999.999.999";//错误版本号(无法更新)
            CTInfo.appName = "班级病毒";
            CTInfo.author = "银狼";
            CTInfo.iconPath = "/image/err/icon.png";
            b = false;
            CTColor.setErrorColor();//修改颜色
        }


        boolean finalStartUpdate = startUpdate;
        boolean finalB = b;

        Log.info.loading.closeDialog("程序加载");

        wait.setVisible(false);
        wait.getLoader().stopAnimation();
        try {
            SwingRun.show(finalB, finalStartUpdate);
        } catch (Exception e) {
            Log.err.print(Main.class, "窗口初始化失败", e);
            Log.showLogDialog(true);
        }


        Log.info.print("Main", "初始化完毕");


    }

    /**
     * 判断是否存在参数
     * @param arg 参数 类型:
     *            <ul>
     *                <li><code>StartUpdate:false</code>
     *                <li><code>screenProduct:show</code>
     *                <li><code>screenProduct:view</code>
     *                <li><code>CTInfo:isError</code>
     *                <li><code>BasicDataPath</code>
     *            <li><code>EasterEgg:notShow</code></li>
     *            </ul>
     *
     * @return 是否存在
     */
    public static boolean isHasTheArg(String arg){
        return allArgs.get(arg).contains(argsList);
    }

    /**
     * 获取当前参数下一位,若不存在传入的参数则返回null
     * @param arg 参数 类型:
     *            <ul>
     *                <li><code>StartUpdate:false</code>
     *                <li><code>screenProduct:show</code>
     *                <li><code>screenProduct:view</code>
     *                <li><code>CTInfo:isError</code>
     *                <li><code>BasicDataPath</code>
     *            <li><code>EasterEgg:notShow</code></li>
     *            </ul>
     *
     * @return 下一位
     */
    public static String getTheArgNextArg(String arg){
        if (allArgs.get(arg).contains(argsList)) {
            ArrayList<String> parameterList = allArgs.get(arg).getParameterList();
            int index = -1;
            for (int i = 0; i < parameterList.size(); i++) {
                int tempIndex = argsList.indexOf(parameterList.get(i));
                if (tempIndex != -1) {
                    index = tempIndex;
                    break;
                }
            }
            return argsList.get(index + 1);
        } else return null;
    }
}