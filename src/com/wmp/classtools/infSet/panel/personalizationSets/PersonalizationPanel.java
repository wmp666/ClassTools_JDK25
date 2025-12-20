package com.wmp.classTools.infSet.panel.personalizationSets;

import com.wmp.classTools.CTComponent.CTPanel.setsPanel.CTListSetsPanel;
import com.wmp.classTools.infSet.panel.personalizationSets.basicSets.PBasicSetsPanel;

public class PersonalizationPanel extends CTListSetsPanel {


    public PersonalizationPanel() {
        super(null);

        this.setName("个性化");

        this.add(new PBasicSetsPanel());
        this.add(new PAppFileSetsPanel());
        this.add(new PAppInfoSetsPanel());
    }
}
