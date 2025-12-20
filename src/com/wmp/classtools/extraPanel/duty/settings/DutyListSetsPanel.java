package com.wmp.classTools.extraPanel.duty.settings;

import com.wmp.PublicTools.io.IOForInfo;
import com.wmp.PublicTools.io.InfProcess;
import com.wmp.PublicTools.printLog.Log;
import com.wmp.classTools.CTComponent.CTPanel.setsPanel.CTTableSetsPanel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DutyListSetsPanel extends CTTableSetsPanel {

    private final File DutyListPath;

    public DutyListSetsPanel(String basicDataPath) {
        super(new String[]{"扫地", "擦黑板"}, null, basicDataPath);
        setName("值日生");

        File dataPath = new File(basicDataPath, "Duty");

        this.DutyListPath = new File(dataPath, "DutyList.txt");

        //初始化
        try {
            String[][] dutyList = getDutyList();
            setArray(dutyList);
        } catch (IOException e) {
            Log.err.print(getClass(), "初始化失败", e);
        }

    }

    private String[][] getDutyList() throws IOException {
        //获取inf
        IOForInfo ioForInfo = new IOForInfo(this.DutyListPath);

        String[] inf = ioForInfo.getInfo();

        //System.out.println(inf);
        if (inf[0].equals("err")) {
            return null;
        }


        //处理inf

        //初级数据-[0]"[xxx][xxx,xxx]" [1]...
        String[] inftempList = inf;

        String[][] list = new String[inftempList.length][2];
        ArrayList<String[]> tempList = new ArrayList<>();

        int i = 0;
        for (String s : inftempList) {
            //二级数据- [0]"xxx" [1]"xxx,xxx"
            //三级数据- [0]{"xxx"} [1]{"xxx","xxx"}
            ArrayList<String> strings = InfProcess.NDExtractNames(s);

            if (strings.size() == 2) {
                list[i][0] = strings.get(0);
                list[i][1] = strings.get(1);
            }
            i++;
        }
        return list;
    }

    @Override
    public void save() {
        //保存数据-dutyList


        //处理表格中的数据


        // 遍历表格中的每一行，将每一行的数据添加到tempList中
        //getRowCount()-行数
        StringBuilder sb = new StringBuilder();
        String[][] array = this.getArray();
        for (int i = 1; i < array.length; i++) {

            //getColumnCount()-列数
            for (int j = 0; j < array[i].length; j++) {

                sb.append("[").append(array[i][j]).append("]");
            }
            sb.append("\n");
        }

        try {
            new IOForInfo(DutyListPath).setInfo(sb.toString());
        } catch (IOException e) {
            Log.err.print(getClass(), "保存失败", e);
        }


    }

    @Override
    public String[][] resetData() {
        try {
            return getDutyList();
        } catch (IOException e) {
            Log.err.print(getClass(), "重置数据失败", e);
            return null;
        }
    }
}
