package com.wmp.classTools.extraPanel.attendance.settings;

import com.wmp.PublicTools.UITools.CTColor;
import com.wmp.PublicTools.UITools.CTFont;
import com.wmp.PublicTools.UITools.CTFontSizeStyle;
import com.wmp.PublicTools.appFileControl.CTInfoControl;
import com.wmp.PublicTools.io.IOForInfo;
import com.wmp.PublicTools.printLog.Log;
import com.wmp.classTools.CTComponent.CTCheckBox;
import com.wmp.classTools.CTComponent.CTPanel.setsPanel.CTSetsPanel;
import com.wmp.classTools.extraPanel.attendance.control.AttInfo;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class LeaveListSetsPanel extends CTSetsPanel<AttInfo> {

    private final ArrayList<CTCheckBox> checkBoxList = new ArrayList<>();

    public LeaveListSetsPanel(CTInfoControl<AttInfo> infoControl) throws IOException {
        super(infoControl);

        setName("迟到人员");

        ArrayList<String> leaveList = getLeaveList();
        ArrayList<String> studentList = getStudentList();
        initATSet(studentList, leaveList);
    }

    private void initATSet(ArrayList<String> studentList, ArrayList<String> leaveList) {

        this.removeAll();
        checkBoxList.clear();

        //this.setBackground(CTColor.backColor);
        this.setLayout(new BorderLayout());

        // 请假人员设置组件
        JLabel leaveLabel = new JLabel("请假人员:");
        leaveLabel.setFont(CTFont.getCTFont(Font.PLAIN, CTFontSizeStyle.NORMAL));
        leaveLabel.setForeground(CTColor.textColor);
        this.add(leaveLabel, BorderLayout.NORTH);

        JPanel leavePanel = new JPanel();
        leavePanel.setBounds(20, 0, 340, 300);
        leavePanel.setBackground(CTColor.backColor);
        leavePanel.setLayout(new GridLayout(studentList.size() / 4 + 1, 4, 10, 10));
        //leavePanel.setBackground(Color.WHITE);


        JScrollPane scrollPane = new JScrollPane(leavePanel);
        //修改滚轮的灵敏度
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);
        this.add(scrollPane, BorderLayout.CENTER);

        Log.info.print("数据设置界面-initATSet", "studentList:" + studentList);
        Log.info.print("数据设置界面-initATSet", "leaveList:" + leaveList);
        for (String student : studentList) {
            CTCheckBox checkBox = new CTCheckBox(student);
            checkBox.setFont(CTFont.getCTFont(Font.PLAIN, CTFontSizeStyle.SMALL));
            checkBox.setBackground(CTColor.backColor);
            checkBox.setForeground(CTColor.textColor);


            if (leaveList.contains(student.trim())) {
                checkBox.setSelected(true);
            }
            checkBoxList.add(checkBox);
            leavePanel.add(checkBox);
        }

    }

    private ArrayList<String> getLeaveList() {
        return new ArrayList<>(Arrays.asList(getInfoControl().getInfo().leaveList()));
    }

    private ArrayList<String> getStudentList() {
        return new ArrayList<>(Arrays.asList(getInfoControl().getInfo().allStuList()));
    }

    @Override
    public void save() {
        //保存数据-请假信息

            ArrayList<String> tempList = new ArrayList<>();
            for (CTCheckBox checkBox : checkBoxList) {
                if (checkBox.isSelected()) {
                    tempList.add(checkBox.getText());
                }
            }
            getInfoControl().setInfo(
                new AttInfo(null,
                        tempList.toArray(new String[0])));

    }

    @Override
    public void refresh() throws IOException {
        getInfoControl().refresh();

        ArrayList<String> leaveList = getLeaveList();
        ArrayList<String> studentList = getStudentList();
        initATSet(studentList, leaveList);
    }
}
