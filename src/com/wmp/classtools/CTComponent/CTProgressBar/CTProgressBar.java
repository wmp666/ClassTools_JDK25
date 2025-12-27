package com.wmp.classTools.CTComponent.CTProgressBar;

import com.wmp.classTools.CTComponent.CTGradientRoundProgressBarUI;

import javax.swing.*;
import java.awt.*;

public class CTProgressBar extends JProgressBar {

    public CTProgressBar() {
        this(0, 100);
    }

    public CTProgressBar(int min, int max) {
        super(min, max);
        this.setOpaque(false);
        this.setBorderPainted(false);
        this.setMinimumSize(new Dimension(100, 20));
        this.setUI(new CTGradientRoundProgressBarUI());


    }
}
