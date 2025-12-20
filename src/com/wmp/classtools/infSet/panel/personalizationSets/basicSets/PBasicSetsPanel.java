package com.wmp.classTools.infSet.panel.personalizationSets.basicSets;

import com.wmp.PublicTools.CTInfo;
import com.wmp.classTools.CTComponent.CTPanel.setsPanel.CTListSetsPanel;

public class PBasicSetsPanel extends CTListSetsPanel {

    public PBasicSetsPanel() {
        super(null);

        setName("基础设置");

        this.clearCTList();
        this.add(new PBBasicSetsPanel());
        this.add(new PBPanelSetsPanel());
    }
}
