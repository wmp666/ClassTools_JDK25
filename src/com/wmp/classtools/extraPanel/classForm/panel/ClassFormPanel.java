package com.wmp.classTools.extraPanel.classForm.panel;

import com.wmp.PublicTools.UITools.CTColor;
import com.wmp.PublicTools.UITools.CTFont;
import com.wmp.PublicTools.UITools.CTFontSizeStyle;
import com.wmp.PublicTools.UITools.PeoPanelProcess;
import com.wmp.PublicTools.appFileControl.CTInfoControl;
import com.wmp.PublicTools.printLog.Log;
import com.wmp.PublicTools.videoView.MediaPlayer;
import com.wmp.classTools.CTComponent.CTPanel.CTViewPanel;
import com.wmp.classTools.extraPanel.classForm.CFInfoControl;
import com.wmp.classTools.extraPanel.classForm.ClassFormInfo;
import com.wmp.classTools.extraPanel.classForm.ClassFormInfos;
import com.wmp.classTools.extraPanel.classForm.settings.ClassFormSetsPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class ClassFormPanel extends CTViewPanel<ClassFormInfos[]> {

    private String oldNowClassName = "无";
    private String oldNextClassName = "无";


    public ClassFormPanel() {
        this.setLayout(new GridBagLayout());
        this.setName("课程表");
        this.setID("ClassFormPanel");
        this.setOpaque(false);
        this.setCtSetsPanelList(java.util.List.of(new ClassFormSetsPanel(getInfoControl())));

        this.setIndependentRefresh(true, 1000);
    }

    @Override
    public CTInfoControl<ClassFormInfos[]> setInfoControl() {
        return new CFInfoControl();
    }

    private void showClassForm(String nowClass, String nextClass) {

        if (!Objects.equals(nowClass, "无")) {
            MediaPlayer.playMusic("课程表", "上课");
        } else {
            MediaPlayer.playMusic("课程表", "下课");
        }

        if (Objects.equals(nowClass, "无") && nextClass.equals("无")) {
            return;
        }


        String infoSB = "本节课:" +
                nowClass +
                "\n" +
                "下节课:" +
                nextClass;

        Log.info.systemPrint("课程提醒", infoSB);
        Log.info.adaptedMessage("课程提醒", infoSB, 60, 5);

    }

    @Override
    protected void easyRefresh() {
        this.removeAll();


        //课程数据
        ClassFormInfo nowClass = ((CFInfoControl) getInfoControl()).getNowClass();
        CFInfoControl.nextClassInfo nextClassInfo = ((CFInfoControl) getInfoControl()).getNextClass();
        String nextClass = nextClassInfo.className();
        try {

            if (nowClass == null) {
                nowClass = new ClassFormInfo("无", "00:00-00:00");
            }
            if (nextClass == null || nextClass.isEmpty()) nextClass = "无";

            // 使用 Objects.equals 来安全比较，避免 NullPointerException
            if (!Objects.equals(oldNowClassName, nowClass.className()) ||
                    !Objects.equals(oldNextClassName, nextClass)) {
                showClassForm(nowClass.className(), nextClass.equals("无") ? "无" : String.format("%s(%s分钟)", nextClass, nextClassInfo.time()));
            }

            // 数据更新
            this.oldNowClassName = nowClass.className();
            this.oldNextClassName = nextClass;


        } catch (Exception e) {
            Log.err.print(getClass(), "获取课程表失败", e);
        }


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel titleLabel = new JLabel("<html>本节课:</html>");
        titleLabel.setForeground(CTColor.textColor);
        titleLabel.setFont(CTFont.getCTFont(Font.BOLD, CTFontSizeStyle.NORMAL));
        this.add(titleLabel, gbc);

        gbc.gridy++;
        this.add(PeoPanelProcess.getShowPeoPanel(List.of(nowClass == null? "无" : nowClass.className())), gbc);

        JLabel titleLabel2 = new JLabel("<html>下节课:</html>");
        titleLabel2.setForeground(CTColor.textColor);
        titleLabel2.setFont(CTFont.getCTFont(Font.BOLD, CTFontSizeStyle.NORMAL));
        gbc.gridy++;
        this.add(titleLabel2, gbc);

        gbc.gridy++;
        this.add(PeoPanelProcess.getShowPeoPanel(List.of(nextClass.equals("无") ? "无" : String.format("%s(%s分钟)", nextClass, nextClassInfo.time()))), gbc);


        this.revalidate();
        this.repaint();
    }
}
