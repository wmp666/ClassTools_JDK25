package com.wmp.classTools.importPanel.newsText.settings;

import com.wmp.publicTools.UITools.CTColor;
import com.wmp.publicTools.UITools.CTFont;
import com.wmp.publicTools.UITools.CTFontSizeStyle;
import com.wmp.publicTools.appFileControl.CTInfoControl;
import com.wmp.classTools.CTComponent.CTButton.CTTextButton;
import com.wmp.classTools.CTComponent.CTPanel.setsPanel.CTBasicSetsPanel;
import com.wmp.classTools.CTComponent.CTTextField;
import com.wmp.classTools.frame.ShowHelpDoc;

import javax.swing.*;
import java.awt.*;

public class NTSetsPanel extends CTBasicSetsPanel<String> {

    private final CTTextField newsTextKeyTextField = new CTTextField();

    public NTSetsPanel(CTInfoControl<String> infoControl) {
        super(infoControl);

        this.setName("新闻数据获取设置");
        this.setLayout(new GridLayout(0, 1));

        initUI();

    }

    private void initUI() {

        initKeyPanel();
    }

    private void initKeyPanel() {
        JPanel keyPanel = new JPanel();
        keyPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        keyPanel.setOpaque(false);


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel label = new JLabel("新闻数据接口密钥: ");
        label.setFont(CTFont.getCTFont(Font.PLAIN, CTFontSizeStyle.NORMAL));
        label.setForeground(CTColor.textColor);
        keyPanel.add(label, gbc);

        newsTextKeyTextField.setColumns(15);
        newsTextKeyTextField.setText(getInfoControl().getInfo());
        newsTextKeyTextField.setFont(CTFont.getCTFont(Font.PLAIN, CTFontSizeStyle.NORMAL));
        gbc.gridx++;
        keyPanel.add(newsTextKeyTextField, gbc);

        CTTextButton helpButton = new CTTextButton("?", false);
        helpButton.setFont(CTFont.getCTFont(Font.PLAIN, CTFontSizeStyle.NORMAL));
        helpButton.addActionListener(e -> ShowHelpDoc.openWebHelpDoc("NewsKeyCodeGetHelp.docx"));
        gbc.gridx++;
        keyPanel.add(helpButton, gbc);

        this.add(keyPanel);
    }

    @Override
    public void save() {
        getInfoControl().setInfo(newsTextKeyTextField.getText());
    }

    @Override
    public void refresh() {
        getInfoControl().refresh();

        this.removeAll();
        initUI();
        this.revalidate();
        this.repaint();
    }
}
