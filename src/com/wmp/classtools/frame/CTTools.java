package com.wmp.classTools.frame;

import com.wmp.publicTools.CTInfo;
import com.wmp.publicTools.CTTool.CTTool;
import com.wmp.publicTools.CTTool.callRoll.CallRollTool;
import com.wmp.publicTools.UITools.CTColor;
import com.wmp.publicTools.UITools.CTFont;
import com.wmp.publicTools.UITools.CTFontSizeStyle;
import com.wmp.publicTools.UITools.GetIcon;
import com.wmp.publicTools.appFileControl.IconControl;
import com.wmp.publicTools.printLog.Log;
import com.wmp.classTools.CTComponent.CTButton.CTRoundTextButton;
import com.wmp.classTools.CTComponent.CTPanel.setsPanel.CTListSetsPanel;
import com.wmp.classTools.CTComponent.CTPanel.setsPanel.CTSetsPanel;
import com.wmp.classTools.CTComponent.Menu.CTMenu;
import com.wmp.classTools.CTComponent.Menu.CTMenuItem;
import com.wmp.classTools.CTComponent.Menu.CTPopupMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.util.ArrayList;

public class CTTools extends JDialog {
    public static CTListSetsPanel CTToolsSetsPanel = new CTListSetsPanel(null);

    private static final ArrayList<CTTools> oldClass = new ArrayList<>();
    public static final ArrayList<CTTool> tools = new ArrayList<>();


    public CTTools() {
        oldClass.forEach(CTTools::dispose);
        oldClass.clear();
        new CTTools(0);
        new CTTools(1);
    }

    /**
     *
     * @param style 0-右 1-左
     */
    private CTTools(int style) {

        oldClass.add(this);

        initFrame();


        tools.clear();
        tools.add(new CallRollTool());



        CTToolsSetsPanel.setName("快捷工具设置");
        CTToolsSetsPanel.clearCTList();
        tools.forEach(tool -> {
            for (CTSetsPanel ctSetsPanel : tool.getCtSetsPanelList()) {
                CTToolsSetsPanel.add(ctSetsPanel);
            }
        });

        CTRoundTextButton openButton = new CTRoundTextButton(style == 0 ? "<" : ">");
        openButton.setFont(CTFont.getCTFont(Font.BOLD, CTFontSizeStyle.NORMAL));
        openButton.addActionListener(e -> showDialog(style));
        this.add(openButton, BorderLayout.CENTER);


        this.pack();
        if (style == 0) {
            this.setShape(new RoundRectangle2D.Double(0, 0, this.getWidth() + CTInfo.arcw, this.getHeight(), CTInfo.arcw, CTInfo.arch));
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            this.setLocation(screenSize.width - this.getWidth(), screenSize.height * 3 / 5);
        } else {
            this.setShape(new RoundRectangle2D.Double(-CTInfo.arcw, 0, this.getWidth() + CTInfo.arcw, this.getHeight(), CTInfo.arcw, CTInfo.arch));
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            this.setLocation(0, screenSize.height * 3 / 5);
        }
        this.setVisible(true);
    }

    /**
     *
     * @param style 0-右 1-左
     */
    public static void showDialog(int style) {
        JDialog dialog = new JDialog();
        dialog.setTitle("快捷工具");
        dialog.setModal(true);
        dialog.getContentPane().setBackground(CTColor.backColor);
        dialog.setLayout(new GridLayout(0, 1, (int) (5 * CTInfo.dpi), (int) (5 * CTInfo.dpi)));


        CTPopupMenu popupMenu = new CTPopupMenu();

        ArrayList<CTMenuItem> ctRoundTextButtonArrayList = new ArrayList<>();
        tools.forEach(tool -> {
            CTMenuItem menuItem = new CTMenuItem(tool.name);
             menuItem.setFont(CTFont.getDefaultFont( Font.BOLD, CTFontSizeStyle.BIG));
            menuItem.addActionListener(ex -> tool.showTool());
            ctRoundTextButtonArrayList.add(menuItem);
            popupMenu.add(menuItem);
        });

        CTMenuItem  menuItem = new CTMenuItem("打开更多工具(快捷启动单元)");
        menuItem.setIcon(GetIcon.getIcon( "通用.快捷工具", IconControl.COLOR_COLORFUL, 24, 24));
        menuItem.setFont(CTFont.getCTFont(Font.BOLD, CTFontSizeStyle.BIG));
        menuItem.addActionListener(ex -> {
            try {
                new ShowCookieDialog();
            } catch (IOException e) {
                Log.err.print(ShowCookieDialog.class, "CookieDialog打开失败", e);
            }
        });
        popupMenu.add(menuItem);

        CTMenu aidToolsMenu = new CTMenu("急救工具");
        aidToolsMenu.setFont(CTFont.getDefaultFont( Font.BOLD, CTFontSizeStyle.BIG));
        for (Component c : Log.getCtPopupMenu().getComponents()) {
            aidToolsMenu.add(c);
        }
        popupMenu.add(aidToolsMenu);

        if (style == 2){
            ctRoundTextButtonArrayList.forEach(dialog::add);
            dialog.add(menuItem);

            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        } else if (style == 0) popupMenu.show(oldClass.get(0), -popupMenu.getWidth() - oldClass.get(0).getWidth(), 0);
        else if (style == 1)popupMenu.show(oldClass.get(1), oldClass.get(1).getWidth(), 0);
    }

    private void initFrame() {
        this.setTitle("快捷工具");
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.setUndecorated(true);
        this.setOpacity(0.7f);
        this.setAlwaysOnTop(true);
        this.setSize(500, 500);
        this.setLayout(new BorderLayout());

        this.addWindowListener( new WindowAdapter(){
            @Override
            public void windowOpened(WindowEvent e) {
                CTTools.this.repaint();
            }
        });

        this.getContentPane().setBackground(CTColor.backColor);
    }
}
