package com.wmp.classTools.frame;

import com.wmp.PublicTools.CTInfo;
import com.wmp.PublicTools.EasterEgg.EETextStyle;
import com.wmp.PublicTools.EasterEgg.EasterEgg;
import com.wmp.PublicTools.UITools.CTColor;
import com.wmp.PublicTools.UITools.CTFont;
import com.wmp.PublicTools.UITools.CTFontSizeStyle;
import com.wmp.PublicTools.UITools.GetIcon;
import com.wmp.PublicTools.appFileControl.IconControl;
import com.wmp.PublicTools.printLog.Log;
import com.wmp.classTools.CTComponent.CTProgressBar.CTProgressBar;
import com.wmp.classTools.CTComponent.CTWindow;

import javax.swing.*;
import java.awt.*;

public class LoadingWindow extends CTWindow {

    public static final int STYLE_NORMAL = 0;
    public static final int STYLE_SCREEN = 1;

    public LoadingWindow() {
        this(180, 180, "useLoadingText", true, 5000, 0);
    }

    public LoadingWindow(String text) {
        this(180, 180, text, false, 0, 0);
    }

    public LoadingWindow(int width, int height, String text) {
        this(width, height, text, false, 0, 0);
    }

    public LoadingWindow(int width, int height, String text, boolean mustWait, long time) {
        this(width, height, text, mustWait, time, 0);
    }

    public LoadingWindow(int width, int height, String text, boolean mustWait, long time, int windowStyle) {

        super();

        Log.info.print("LoadingWindow-窗口", "开始初始化加载窗口");

        this.setLayout(new BorderLayout());

        time = initUI(width, height, text, time);

        this.setTitle("正在加载班级工具");
        this.setIconImage(GetIcon.getImageIcon("通用.进度", IconControl.COLOR_COLORFUL, 48, 48).getImage());
        this.getContentPane().setBackground(CTColor.backColor);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(this.getPreferredSize());
        this.setLocationRelativeTo(null);
        this.getContentPane().setCursor(
                Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        Taskbar taskbar = Taskbar.getTaskbar();
        if (taskbar.isSupported(Taskbar.Feature.PROGRESS_STATE_WINDOW)) {
            taskbar.setWindowProgressState(this, Taskbar.State.INDETERMINATE);
        }

        this.setVisible(true);
        try {
            if (mustWait) {
                Thread.sleep(time);
            } else if (text.equals("useLoadingText")) {
                Thread.sleep(time);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Log.info.print("LoadingWindow-窗口", "加载窗口初始化完毕");

    }

    private long initUI(int width, int height, String text, long time) {
        String showText = text;
        if (showText.equals("useLoadingText") || showText.equals("EasterEgg")) {
            showText = getLoadingText();
        }

        // 根据文字数量调整窗口大小
        String plainText = showText.replaceAll("<html>|</html>", "").replaceAll("<br>", "\n"); // 去除HTML标签
        // 计算新的窗口尺寸（基础尺寸 + 动态调整）
        time = Math.max(time, plainText.length() * 90L);

        ImageIcon easterEgg = GetIcon.getImageIcon(text.equals("EasterEgg") ? "系统.加载.胡桃" : "系统.图标", IconControl.COLOR_DEFAULT, width, height);

        JLabel label = new JLabel(showText, easterEgg, SwingConstants.CENTER);
        easterEgg.setImageObserver(label);
        label.setForeground(CTColor.textColor);
        label.setFont(CTFont.getCTFont(Font.BOLD, CTFontSizeStyle.NORMAL));
        this.add(label, BorderLayout.CENTER);

        CTProgressBar progressBar = new CTProgressBar();
        progressBar.setIndeterminate(true);
        this.add(progressBar, BorderLayout.SOUTH);

        JLabel loadingText = new JLabel(String.format("%s 版本:%s 正在加载...", CTInfo.appName, CTInfo.version));
        loadingText.setForeground(CTColor.textColor);
        loadingText.setFont(CTFont.getCTFont(Font.BOLD, CTFontSizeStyle.NORMAL));
        this.add(loadingText, BorderLayout.NORTH);
        return time;
    }

    private String getLoadingText() {
        String easterEgg = EasterEgg.getText(EETextStyle.HTML);
        return easterEgg;
    }


}
