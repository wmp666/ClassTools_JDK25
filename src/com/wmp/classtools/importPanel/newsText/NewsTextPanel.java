package com.wmp.classTools.importPanel.newsText;

import com.wmp.Main;
import com.wmp.PublicTools.UITools.CTColor;
import com.wmp.PublicTools.UITools.CTFont;
import com.wmp.PublicTools.UITools.CTFontSizeStyle;
import com.wmp.PublicTools.UITools.GetMaxSize;
import com.wmp.PublicTools.appFileControl.CTInfoControl;
import com.wmp.PublicTools.printLog.Log;
import com.wmp.PublicTools.web.GetWebInf;
import com.wmp.classTools.CTComponent.CTButton.CTRoundTextButton;
import com.wmp.classTools.CTComponent.CTPanel.CTViewPanel;
import com.wmp.classTools.CTComponent.Menu.CTPopupMenu;
import com.wmp.classTools.importPanel.newsText.settings.NTSetsPanel;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class NewsTextPanel extends CTViewPanel<String> {

    private static final String url = "https://whyta.cn/api/tx/guonei?key=%s&num=50";

    //private final JPanel showPanel;
    private final JTextArea textArea = new JTextArea();
    private final AtomicInteger i = new AtomicInteger(-1);
    private int index = 0;
    private String webInf = "";


    public NewsTextPanel() {
        this.setLayout(new BorderLayout());
        this.setName("新闻页");
        this.setID("NewsTextPanel");
        this.setOpaque(false);

        this.setCtSetsPanelList(List.of(new NTSetsPanel(getInfoControl())));

        this.setIndependentRefresh(true, 20 * 60 * 1000);

        try {
            webInf = GetWebInf.getWebInf(String.format(url, getInfoControl().getInfo()),
                    false);
        } catch (Exception e) {
            Log.warn.print(getClass().getName(), "获取新闻失败");
        }

        Timer refreshTimer = new Timer(2 * 60 * 60 * 1000, e -> {
            try {
                webInf = GetWebInf.getWebInf(String.format(url, getInfoControl().getInfo()),
                        false);
            } catch (Exception ex) {
                Log.warn.print(getClass().getName(), "获取新闻失败");
            }
        });
        refreshTimer.start();

        Timer indexRefreshTimer = new Timer(2 * 60 * 60 * 1000, e -> index++);
        indexRefreshTimer.start();

        Timer controlTimer = new Timer(60 * 1000, e -> {
            if (!this.isVisible()) {
                refreshTimer.stop();
                indexRefreshTimer.stop();
            } else {
                refreshTimer.start();
                indexRefreshTimer.start();
            }
        });
        controlTimer.start();

    }

    @Override
    public CTInfoControl<String> setInfoControl() {
        return new NewsTextControl();
    }

    @Override
    protected void easyRefresh() {
        this.removeAll();
        //showPanel.removeAll();

        try {
            if (webInf.isEmpty()) {
                return;
            }
            JSONObject jsonObject = new JSONObject(webInf);


            jsonObject.getJSONObject("result").getJSONArray("newslist").forEach(item -> {


                if (index >= jsonObject.getJSONObject("result").getInt("allnum")) {
                    i.set(-1);
                }
                if (i.get() >= jsonObject.getJSONObject("result").getInt("allnum")) {
                    i.set(-1);
                }

                i.getAndIncrement();

                if (i.get() != index) {
                    return;
                }

                if (item instanceof JSONObject itemObject) {
                    String s = itemObject.getString("title");
                    StringBuilder sb = new StringBuilder();
                    for (char c : s.toCharArray()) {
                        String s1 = "？！。 ?!;；:：";
                        sb.append(c);
                        if (s.indexOf(c) != s.length() - 1) {
                            sb.append((s1.contains(String.valueOf(c))) ? "\n" : "");
                        }

                    }
                    textArea.setText(sb.toString());
                    textArea.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    textArea.setEditable(false);
                    textArea.setOpaque(false);
                    textArea.setForeground(CTColor.mainColor);
                    textArea.setFont(CTFont.getCTFont(Font.BOLD, CTFontSizeStyle.BIG));
                    textArea.removeMouseListener(textArea.getMouseListeners()[textArea.getMouseListeners().length - 1]);

                    textArea.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (e.getButton() == MouseEvent.BUTTON1) {
                                if (!Main.isHasTheArg("screenProduct:show")) {
                                    try {
                                        Desktop.getDesktop().browse(URI.create(itemObject.getString("url")));
                                    } catch (Exception ex) {
                                        Log.err.print(getClass(), "无法打开网页", ex);
                                    }
                                }
                            } else if (e.getButton() == MouseEvent.BUTTON3) {
                                CTPopupMenu popupMenu = new CTPopupMenu();
                                if (!Main.isHasTheArg("screenProduct:show")) {
                                    CTRoundTextButton openURL = new CTRoundTextButton("查看详情");
                                    openURL.addActionListener(e1 -> {
                                        try {
                                            Desktop.getDesktop().browse(URI.create(itemObject.getString("url")));
                                        } catch (Exception ex) {
                                            Log.err.print(getClass(), "无法打开网页", ex);
                                        }
                                    });
                                    popupMenu.add(openURL);
                                }

                                CTRoundTextButton next = new CTRoundTextButton("下一个");
                                next.addActionListener(e1 -> {
                                    index++;
                                    try {
                                        easyRefresh();
                                    } catch (Exception ex) {
                                        Log.err.print(getClass(), "刷新失败", ex);
                                    }
                                });
                                popupMenu.add(next);

                                CTRoundTextButton refresh = new CTRoundTextButton("刷新");
                                refresh.addActionListener(e1 -> {
                                    try {
                                        webInf = GetWebInf.getWebInf(String.format("https://whyta.cn/api/tx/guonei?key=%s&num=50", getInfoControl().getInfo()),
                                                false);
                                        index = 0;
                                        easyRefresh();
                                    } catch (Exception ex) {
                                        Log.err.print(getClass(), "刷新失败", ex);
                                    }
                                });
                                popupMenu.add(refresh);

                                popupMenu.show(textArea, e.getX(), e.getY());
                            }
                        }
                    });
                }


            });
            FontMetrics fm = textArea.getFontMetrics(textArea.getFont());
            // 根据文字数量调整窗口大小
            int lineCount = GetMaxSize.getLine(textArea.getText(), GetMaxSize.STYLE_HTML);// 行数
            // 计算新的窗口尺寸（基础尺寸 + 动态调整）
            int newWidth = GetMaxSize.getHTMLToTextMaxLength(textArea.getText(), fm) + 10; // 根据最大字符宽度计算总宽度
            int newHeight = lineCount * (textArea.getFont().getSize() + 5);  // 每多一行增加30像素高度

            int maxShowHeight = (this.isScreenProductViewPanel() ? 6 : 4) * textArea.getFont().getSize(); // 最大显示高度

            // 设置窗口大小
            if (newHeight >= maxShowHeight) {
                newHeight = maxShowHeight;
            }

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.getViewport().setOpaque(false);
            scrollPane.setOpaque(false);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());


            this.add(scrollPane, BorderLayout.CENTER);
            scrollPane.setPreferredSize(new Dimension(this.isScreenProductViewPanel() ? Toolkit.getDefaultToolkit().getScreenSize().width : newWidth, newHeight + 20));

            // 添加以下两行代码使滚动条回到顶部
            textArea.setCaretPosition(0);

            Log.info.systemPrint("新闻", textArea.getText());
        } catch (Exception e) {
            Log.warn.print(getClass().getName(), "获取新闻失败");
            throw e;
        }


        this.revalidate();
        this.repaint();

    }
}
