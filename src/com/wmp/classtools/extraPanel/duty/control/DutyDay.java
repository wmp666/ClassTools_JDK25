package com.wmp.classTools.extraPanel.duty.control;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 用于存储单个值班信息
 */
public class DutyDay {

    private ArrayList<String> ClFloorList;

    private ArrayList<String> ClBlackBroadList;

    public DutyDay() {
    }

    public DutyDay(ArrayList<String> clFloorList, ArrayList<String> clBlackBroadList) {
        ClFloorList = clFloorList;
        ClBlackBroadList = clBlackBroadList;
    }

    public static ArrayList<String> setDutyPersonList(String... people) {
        return new ArrayList<>(Arrays.asList(people));
    }

    public ArrayList<String> getClFloorList() {
        return ClFloorList;
    }

    public ArrayList<String> getClBlackBroadList() {
        return ClBlackBroadList;
    }


    @Override
    public String toString() {
        return "DutyDay{" +
                "ClFloorList=" + ClFloorList +
                ", ClBlackBroadList=" + ClBlackBroadList +
                '}' + "\n";
    }

}
