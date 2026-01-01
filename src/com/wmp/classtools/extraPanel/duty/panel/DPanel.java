package com.wmp.classTools.extraPanel.duty.panel;

import com.wmp.PublicTools.UITools.CTColor;
import com.wmp.PublicTools.UITools.CTFont;
import com.wmp.PublicTools.UITools.CTFontSizeStyle;
import com.wmp.PublicTools.UITools.PeoPanelProcess;
import com.wmp.PublicTools.appFileControl.CTInfoControl;
import com.wmp.PublicTools.appFileControl.IconControl;
import com.wmp.PublicTools.printLog.Log;
import com.wmp.classTools.CTComponent.CTButton.CTIconButton;
import com.wmp.classTools.CTComponent.CTPanel.CTViewPanel;
import com.wmp.classTools.extraPanel.duty.control.DutyControl;
import com.wmp.classTools.extraPanel.duty.control.DutyInfo;
import com.wmp.classTools.extraPanel.duty.settings.DutyListSetsPanel;
import com.wmp.classTools.extraPanel.duty.control.DutyDay;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DPanel extends CTViewPanel<DutyInfo> {

    private int index = 0; //当前日期索引

    public DPanel(){

        this.setCtSetsPanelList(List.of(new DutyListSetsPanel(getInfoControl())));
        this.setName("值日表组件");
        this.setID("DPanel");
        //设置容器布局- 绝对布局
        this.setLayout(new BorderLayout());

        initContainer();

        showDutyForm();

    }

    private void initContainer(){
        index = getInfoControl().getInfo().index();

        JPanel InfoPanel = new JPanel();
        InfoPanel.setLayout(new GridBagLayout());
        InfoPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        //gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.WEST;// 左对齐

        JLabel CLBBLabel = new JLabel();
        CLBBLabel.setText("擦黑板 + 倒垃圾:");
        CLBBLabel.setFont(CTFont.getCTFont(Font.BOLD, CTFontSizeStyle.NORMAL));
        CLBBLabel.setForeground(CTColor.textColor);

        InfoPanel.add(CLBBLabel, gbc);

        DutyDay now;
        try {
            now = getInfoControl().getInfo().dutyDay()[index];
        } catch (Exception e) {
            now = getInfoControl().getInfo().dutyDay()[0];
            Log.err.print(getClass(), "数据异常,请检查数据文件", e);
        }

        initPeople(now.getClBlackBroadList(), gbc, InfoPanel);


        JLabel CLFLabel = new JLabel();
        CLFLabel.setText("扫地:");
        CLFLabel.setFont(CTFont.getCTFont(Font.BOLD, CTFontSizeStyle.NORMAL));
        CLFLabel.setForeground(CTColor.textColor);
        gbc.gridy++;
        InfoPanel.add(CLFLabel, gbc);


        initPeople(now.getClFloorList(), gbc, InfoPanel);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setOpaque(false);
        //日期切换按钮
        {

            CTIconButton last = new CTIconButton("上一天",
                    "上一天", IconControl.COLOR_COLORFUL, () -> {
                int i = Log.info.showChooseDialog(this, "CTViewPanel-DutyPanel-日期切换", "确认切换至上一天");
                if (i == 0) {
                    if (index > 0) index--;
                    else index = getInfoControl().getInfo().dutyDay().length - 1;
                }


                try {
                    getInfoControl().setInfo(new DutyInfo(null, index));
                    easyRefresh();
                } catch (IOException ex) {
                    Log.err.print(getClass(), "切换失败", ex);
                }
            });
            buttonPanel.add(last, BorderLayout.WEST);
        }

        {

            CTIconButton next = new CTIconButton("下一天",
                    "下一天", IconControl.COLOR_COLORFUL, () -> {

                int i = Log.info.showChooseDialog(this, "CTViewPanel-DutyPanel-日期切换", "确认切换至下一天");

                if (i == 0) {
                    if (index < getInfoControl().getInfo().dutyDay().length - 1) index++;

                    else index = 0;
                }


                try {
                    getInfoControl().setInfo(new DutyInfo(null, index));
                    easyRefresh();
                } catch (IOException ex) {
                    Log.err.print(getClass(), "切换失败", ex);
                }
            });

            buttonPanel.add(next, BorderLayout.EAST);
        }

        this.add(InfoPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.NORTH);
    }

    private void initPeople(ArrayList<String> array, GridBagConstraints gbc, Container c) {
        if (array == null || array.isEmpty()) {
            return;
        }

        JScrollPane showPeoPanel = PeoPanelProcess.getShowPeoPanel(array);

        gbc.gridy++;
        c.add(showPeoPanel, gbc);

    }

    @Override
    public CTInfoControl<DutyInfo> setInfoControl() {
        return new DutyControl();
    }

    // 刷新方法
    @Override
    protected void easyRefresh() throws IOException {

        this.removeAll();

        initContainer();

        showDutyForm();


        revalidate();
        repaint();


    }

    private void showDutyForm() {
        DutyDay[] dutyList = getInfoControl().getInfo().dutyDay();
        StringBuilder sb = new StringBuilder();
        DutyDay todayDutyForm = dutyList[getInfoControl().getInfo().index()];
        sb.append("值日名单:\n")
                .append("擦黑板: ").append(todayDutyForm.getClBlackBroadList()).append("\n")
                .append("扫地: ").append(todayDutyForm.getClFloorList()).append("\n");
        Log.info.systemPrint("值日表", sb.toString());
    }
}
