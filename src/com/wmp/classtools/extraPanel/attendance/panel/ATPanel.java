package com.wmp.classTools.extraPanel.attendance.panel;

import com.wmp.PublicTools.UITools.CTColor;
import com.wmp.PublicTools.UITools.CTFont;
import com.wmp.PublicTools.UITools.CTFontSizeStyle;
import com.wmp.PublicTools.UITools.PeoPanelProcess;
import com.wmp.PublicTools.appFileControl.CTInfoControl;
import com.wmp.PublicTools.io.IOForInfo;
import com.wmp.PublicTools.printLog.Log;
import com.wmp.classTools.CTComponent.CTPanel.CTViewPanel;
import com.wmp.classTools.CTComponent.CTPanel.setsPanel.CTSetsPanel;
import com.wmp.classTools.extraPanel.attendance.control.AttInfo;
import com.wmp.classTools.extraPanel.attendance.control.AttInfoControl;
import com.wmp.classTools.extraPanel.attendance.settings.AllStuSetsPanel;
import com.wmp.classTools.extraPanel.attendance.settings.LeaveListSetsPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ATPanel extends CTViewPanel<AttInfo> {

    private final JLabel StuInfoLabel = new JLabel();

    private final ArrayList<String> studentList = new ArrayList<>();
    private final ArrayList<String> leaveList = new ArrayList<>();//迟到人员
    private int allStuLength;// 应到人数
    private int lateStudentLength; // 请假人数

    public ATPanel() throws IOException {
        super();


        ArrayList<CTSetsPanel> list = new ArrayList<>();
        list.add(new LeaveListSetsPanel(getInfoControl()));
        list.add(new AllStuSetsPanel(getInfoControl()));
        this.setCtSetsPanelList(list);
        this.setName("考勤表组件");
        this.setID("ATPanel");
        this.setLayout(new GridBagLayout());

        initStuList();

        initContainer();


    }

    private void initContainer() {
        //将CTColor.mainColor解析为16进制颜色

        String StrMainColor = String.format("#%06x", CTColor.mainColor.getRGB());
        StrMainColor = StrMainColor.substring(2, 9);
        String NumColor = "style='color: " + StrMainColor + ";'";

        String StrTextColor = String.format("#%06x", CTColor.textColor.getRGB());
        StrTextColor = StrTextColor.substring(2, 9);
        String TextColor = "style='color: " + StrTextColor + ";'";


        StuInfoLabel.setText("<html>"
                + "<span " + TextColor + ">" + "应到：<span " + NumColor + ">" + allStuLength + "人<br>"
                + "<span " + TextColor + ">" + "实到：<span " + NumColor + ">" + (allStuLength - lateStudentLength) + "人<br>"
                + "<span " + TextColor + ">" + "请假：<span style='color: red;'>" + lateStudentLength + "人"
                + "</html>");
        StuInfoLabel.setFont(CTFont.getCTFont(Font.BOLD, CTFontSizeStyle.BIG));


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;// 列
        gbc.gridy = 0;// 行
        gbc.fill = GridBagConstraints.BOTH;// 填充方式


        this.add(StuInfoLabel, gbc);

        if (leaveList.isEmpty()) {

            ArrayList<String> temp = new ArrayList<>();
            temp.add("无请假人员");
            JScrollPane showPeoPanel = PeoPanelProcess.getShowPeoPanel(temp);
            gbc.gridy++;// 列
            this.add(showPeoPanel, gbc);
        } else {
            JScrollPane showPeoPanel = PeoPanelProcess.getShowPeoPanel(leaveList);
            gbc.gridy++;// 列
            this.add(showPeoPanel, gbc);
        }


    }

    @Override
    public CTInfoControl<AttInfo> setInfoControl() {
        return new AttInfoControl();
    }

    @Override
    protected void easyRefresh() throws IOException {
        // 清空旧数据
        studentList.clear();
        leaveList.clear();
        getInfoControl().refresh();

        // 重新加载数据
        initStuList();

        // 更新UI组件
        this.removeAll();

        initContainer();

        // 强制重绘
        revalidate();
        repaint();
    }

    private void initStuList(){
        //获取所有学生名单
        String[] stuList = getInfoControl().getInfo().allStuList();
        studentList.addAll(Arrays.asList(stuList));
        allStuLength = stuList.length;

        String[] strings = getInfoControl().getInfo().leaveList();
        leaveList.addAll(Arrays.asList(strings));
        lateStudentLength = strings.length;

    }


}
