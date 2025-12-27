package com.wmp.classTools.infSet.panel.personalizationSets.basicSets;

import com.wmp.PublicTools.CTInfo;
import com.wmp.classTools.CTComponent.CTPanel.setsPanel.CTListSetsPanel;
import com.wmp.classTools.infSet.panel.personalizationSets.control.PBasicInfoControl;
import com.wmp.classTools.infSet.panel.personalizationSets.control.PPanelInfoControl;

public class PBasicSetsPanel extends CTListSetsPanel {

    public PBasicSetsPanel() {
        super(null);

        setName("基础设置");

        this.clearCTList();
        this.add(new PBBasicSetsPanel(new PBasicInfoControl()));
        this.add(new PBPanelSetsPanel(new PPanelInfoControl()));
    }
}
