package com.wmp.classTools.extraPanel.reminderBir.panel;

import com.wmp.PublicTools.DateTools;
import com.wmp.PublicTools.UITools.CTColor;
import com.wmp.PublicTools.UITools.CTFont;
import com.wmp.PublicTools.UITools.CTFontSizeStyle;
import com.wmp.PublicTools.UITools.PeoPanelProcess;
import com.wmp.PublicTools.appFileControl.CTInfoControl;
import com.wmp.PublicTools.printLog.Log;
import com.wmp.classTools.CTComponent.CTOptionPane;
import com.wmp.classTools.CTComponent.CTPanel.CTViewPanel;
import com.wmp.classTools.extraPanel.reminderBir.control.BRInfo;
import com.wmp.classTools.extraPanel.reminderBir.control.BRInfoControl;
import com.wmp.classTools.extraPanel.reminderBir.settings.BRSetsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;


public class BRPanel extends CTViewPanel<BRInfo[]> {

    private final ArrayList<String> oldBRNameList = new ArrayList<>();

    private final ArrayList<String> oldWBNameList = new ArrayList<>();

    public BRPanel() {
        this.setLayout(new GridBagLayout());
        this.setName("生日提醒页");
        this.setID("BRPanel");
        this.setOpaque(false);
        this.setCtSetsPanelList(List.of(new BRSetsPanel(getInfoControl())));

        this.setIndependentRefresh(true, 2 * 60 * 1000);
    }

    private void showBRAndWB(){
        List<String> BRTempList = getBRList();
        StringBuilder BRsb = new StringBuilder();

        List<String> WBTempList = getWBList();
        StringBuilder WBsb = new StringBuilder();

        if ((!WBTempList.contains("无人即将生日") && !WBTempList.contains("没有相关数据")) ||
                !BRTempList.contains("无人生日") && !BRTempList.contains("没有相关数据")) {
            for (int i = 0; i < BRTempList.size(); i++) {
                if (i != BRTempList.size() - 1)
                    BRsb.append(BRTempList.get(i)).append(", ");
                else BRsb.append(BRTempList.get(i));

            }

            for (int i = 0; i < WBTempList.size(); i++) {
                if (i != WBTempList.size() - 1)
                    WBsb.append(WBTempList.get(i)).append(", ");
                else WBsb.append(WBTempList.get(i));

            }
            CTOptionPane.showFullScreenMessageDialog("生日祝福", String.format("今日生日:%s\n即将生日:%s", BRsb, WBsb), 60, 5);
        }

    }

    private java.util.List<String> getBRList(){
        ArrayList<String> nameList = new ArrayList<>();

        BRInfo[] brInfos = getInfoControl().getInfo();
        for (BRInfo brInfo : brInfos) {
            if (DateTools.dayIsNow(brInfo.birthday())) {
                nameList.add(brInfo.name());
            }
        }
        if (!nameList.isEmpty()) {
            return nameList;
        }

        return List.of("无人生日");
    }

    /**
     * 获取即将生日列表
     *
     * @return 即将生日列表
     */
    private java.util.List<String> getWBList(){

        ArrayList<String> nameList = new ArrayList<>();

        BRInfo[] brInfos = getInfoControl().getInfo();
        for (BRInfo brInfo : brInfos) {
            int remainderDay = DateTools.getRemainderDay(brInfo.birthday());
            if (remainderDay < 11 && remainderDay > 0) {
                nameList.add(String.format("%s(差%d天)", brInfo.name(), remainderDay));
            }
        }
        if (!nameList.isEmpty()) {
            return nameList;
        }

        return List.of("无人即将生日");
    }

    @Override
    public CTInfoControl<BRInfo[]> setInfoControl() {
        return new BRInfoControl();
    }

    @Override
    protected void easyRefresh() {

        this.removeAll();


        try {

            List<String> BRTempList = getBRList();
            List<String> WBTempList = getWBList();

            if (!oldBRNameList.equals(BRTempList) || !oldWBNameList.equals(WBTempList)) showBRAndWB();

            this.oldBRNameList.clear();
            this.oldBRNameList.addAll(BRTempList);

            this.oldWBNameList.clear();
            this.oldWBNameList.addAll(WBTempList);


        } catch (Exception e) {
            Log.err.print(getClass(), "获取生日列表失败", e);
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;


        JLabel titleLabel = new JLabel("<html>今日过生日:</html>");
        titleLabel.setForeground(CTColor.textColor);
        titleLabel.setFont(CTFont.getCTFont(Font.BOLD, CTFontSizeStyle.NORMAL));
        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    showBRAndWB();
                } catch (Exception ex) {
                    Log.err.print(getClass(), "显示生日列表失败", ex);
                }
            }
        });
        this.add(titleLabel, gbc);

        gbc.gridy++;
        this.add(PeoPanelProcess.getShowPeoPanel(oldBRNameList), gbc);

        //EasterEgg.getText(EETextStyle.HTML)


        JLabel titleLabel2 = new JLabel("<html>即将过生日:</html>");
        titleLabel2.setForeground(CTColor.textColor);
        titleLabel2.setFont(CTFont.getCTFont(Font.BOLD, CTFontSizeStyle.NORMAL));
        titleLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    showBRAndWB();
                } catch (Exception ex) {
                    Log.err.print(getClass(), "显示生日列表失败", ex);
                }
            }
        });
        gbc.gridy++;
        this.add(titleLabel2, gbc);

        gbc.gridy++;
        this.add(PeoPanelProcess.getShowPeoPanel(oldWBNameList), gbc);


        this.revalidate();
        this.repaint();
    }
}
