package com.wmp.classTools.test.uiTest;

import com.wmp.classTools.CTComponent.CTProgressBar.ModernLoadingDialog;

import javax.swing.*;

public class LoadingTest {
    public static void main(String[] args) {
        ModernLoadingDialog wait = new ModernLoadingDialog(null);
        wait.setAlwaysOnTop(true);
        wait.getLoader().startAnimation();
        SwingUtilities.invokeLater(()->wait.setVisible(true));

    }
}
