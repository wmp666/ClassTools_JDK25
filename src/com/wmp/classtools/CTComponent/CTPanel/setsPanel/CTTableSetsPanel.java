package com.wmp.classTools.CTComponent.CTPanel.setsPanel;

import com.wmp.PublicTools.appFileControl.IconControl;
import com.wmp.PublicTools.printLog.Log;
import com.wmp.classTools.CTComponent.CTButton.CTTextButton;
import com.wmp.classTools.CTComponent.CTTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class CTTableSetsPanel extends CTBasicSetsPanel{

    private final CTTable BRTable = new CTTable();
    private String[] titleArray = {"", ""};

    public CTTableSetsPanel(String[] titleArray, String[][] array, String basicDataPath) {
        super(basicDataPath);
        setName("表格设置页");
        this.titleArray = titleArray;

        initTable(titleArray, array);
    }

    private void initTable(String[] titleArray, String[][] array){
        this.removeAll();

        this.setLayout(new BorderLayout());

        DefaultTableModel model = null;
        model = new DefaultTableModel(Objects.requireNonNullElseGet(array, () -> new String[][]{}),
                titleArray);
        BRTable.setModel(model);
        //禁用编辑
        BRTable.setCellEditor( null);

        JScrollPane scrollPane = new JScrollPane(BRTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        this.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        //新建
        {

            CTTextButton newBtn = new CTTextButton("添加");
            newBtn.setIcon("添加", IconControl.COLOR_COLORFUL, 30, 30);
            DefaultTableModel finalModel = model;
            newBtn.addActionListener(e -> {
                String[] strings = addToTable();

                if (strings != null) {
                    finalModel.addRow(strings);
                }
            });
            buttonPanel.add(newBtn);
        }

        // 删除
        {
            CTTextButton deleteBtn = new CTTextButton("删除");
            deleteBtn.setIcon("删除", IconControl.COLOR_COLORFUL, 30, 30);
            DefaultTableModel finalModel1 = model;
            deleteBtn.addActionListener(_ -> {
                deleteToTable(finalModel1);
            });


            buttonPanel.add(deleteBtn);
        }

        // 修改
        {
            CTTextButton removeBtn = new CTTextButton("修改");
            removeBtn.setIcon("刷新", IconControl.COLOR_COLORFUL, 30, 30);
            DefaultTableModel finalModel1 = model;
            removeBtn.addActionListener(_ -> {
                int selectedRow = BRTable.getSelectedRow();
                String[] strings = null;
                if (selectedRow != -1) {
                    strings = new String[finalModel1.getColumnCount()];
                    for (int i = 0; i < finalModel1.getColumnCount(); i++) {
                        strings[i] = finalModel1.getValueAt(selectedRow, i).toString();
                    }
                    String[] newArray = removeToTable(strings);
                    if (newArray != null) {
                        for (int i = 0; i < finalModel1.getColumnCount(); i++) {
                            finalModel1.setValueAt(newArray[i], selectedRow, i);
                        }
                    }
                }

            });


            buttonPanel.add(removeBtn);
        }

        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    public String[] addToTable(){
        ArrayList<String> arrays = new ArrayList<>();

        for (String string : titleArray) {

            String s = Log.info.showInputDialog(this, "CTTableSetsPanel-新建", "请输入" + string);
            if (s == null || s.trim().isEmpty()) {
                return null;
            }

            arrays.add(s);

        }
        return arrays.toArray(new String[0]);
    }

    public void deleteToTable(DefaultTableModel model){
        int selectedRow = BRTable.getSelectedRow();
        if (selectedRow != -1) {
            model.removeRow(selectedRow);
        }
    }

    public String[] removeToTable(String[] oldArray){
        ArrayList<String> arrays = new ArrayList<>();

        for (int i = 0; i < titleArray.length; i++) {
            String s = Log.info.showInputDialog(this, "CTTableSetsPanel-修改",
                    String.format("原数据:%s\n请输入%s\n注:若不修改不用输入内容", oldArray[i], titleArray[i])
                    );
            if (s == null || s.trim().isEmpty()) {
                arrays.add(oldArray[i]);
            }else arrays.add(s);
        }
        return arrays.toArray(new String[0]);
    }

    public String[][] getArray() {
        Object[][] data = BRTable.getData();

        String[][] result = new String[data.length + 1][data[0].length];
        result[0] = titleArray;
        for (int i = 0; i < data.length; i++) {
            ArrayList<String> arrays = new ArrayList<>();
            for (int j = 0; j < data[i].length; j++) {
                arrays.add(data[i][j].toString());
            }
            result[i + 1] = arrays.toArray(new String[0]);
        }
        return result;
    }

    public void setArray(String[][] array){
        this.removeAll();
        initTable(titleArray, array);

        this.revalidate();
        this.repaint();
    }
    @Override
    public void refresh() throws Exception {
        this.removeAll();
        //初始化
        initTable(titleArray,  BRTable.getStrData());

        this.revalidate();
        this.repaint();
    }
}
