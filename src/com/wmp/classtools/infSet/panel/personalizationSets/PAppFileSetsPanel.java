package com.wmp.classTools.infSet.panel.personalizationSets;

import com.wmp.PublicTools.UITools.CTColor;
import com.wmp.PublicTools.UITools.CTFont;
import com.wmp.PublicTools.UITools.CTFontSizeStyle;
import com.wmp.PublicTools.appFileControl.IconControl;
import com.wmp.PublicTools.appFileControl.AudioControl;
import com.wmp.PublicTools.io.GetPath;
import com.wmp.PublicTools.printLog.Log;
import com.wmp.PublicTools.web.GetWebInf;
import com.wmp.classTools.CTComponent.CTBorderFactory;
import com.wmp.classTools.CTComponent.CTButton.CTTextButton;
import com.wmp.classTools.CTComponent.CTOptionPane;
import com.wmp.classTools.CTComponent.CTPanel.setsPanel.CTBasicSetsPanel;
import com.wmp.classTools.frame.ShowHelpDoc;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.atomic.AtomicReference;

public class PAppFileSetsPanel extends CTBasicSetsPanel {
    public PAppFileSetsPanel() {
        super(null);
        setName("文件设置");

        initUI();
    }

    private void initUI() {
        this.setBackground(CTColor.backColor);
        this.setLayout(new GridLayout(0, 1, 5, 5));

        initImageSetsPanel();

        initMusicSetsPanel();

        initParentDataPathSetsPanel();
    }

    private void initParentDataPathSetsPanel() {
        JPanel dataPathSetsPanel = new JPanel();
        dataPathSetsPanel.setOpaque(false);
        dataPathSetsPanel.setBorder(CTBorderFactory.createTitledBorder("数据父路径设置"));
        dataPathSetsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel dataPathLabel = new JLabel("数据父路径：");
        {
            String path = System.getenv("LOCALAPPDATA");
            if (path != null && !path.isEmpty()){
                File file = new File(path, "\\ClassTools\\basicDataPath.txt");
                if (file.exists() && file.isFile()) {
                    try {
                        path = new File(Files.readString(file.toPath(), StandardCharsets.UTF_8)).getAbsolutePath();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            dataPathLabel.setText("<html>数据父路径：<br>" + path + "</html>");
        }
        dataPathLabel.setFont(CTFont.getCTFont(Font.BOLD, CTFontSizeStyle.BIG));
        dataPathLabel.setForeground(CTColor.textColor);
        dataPathSetsPanel.add(dataPathLabel);

        CTTextButton dataPathButton = new CTTextButton("修改");
        dataPathButton.setFont(CTFont.getCTFont(Font.BOLD, CTFontSizeStyle.BIG));
        dataPathButton.addActionListener(e -> {

            String path = GetPath.getDirectoryPath(this, "选择数据父路径");
            //加载基础目录
            String localPath = System.getenv("LOCALAPPDATA");
            if (localPath != null && !localPath.isEmpty()){
                File file = new File(localPath, "\\ClassTools\\basicDataPath.txt");
                if (path != null) {
                    try {
                        Files.writeString(file.toPath(), path, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                        dataPathLabel.setText("<html>数据父路径：<br>" + localPath + "</html>");
                    } catch (IOException ex) {
                        Log.err.print(getClass(), "数据父路径保存失败", ex);
                        throw new RuntimeException(ex);
                    }
                }
            }

        });
        dataPathSetsPanel.add(dataPathButton);

        this.add(dataPathSetsPanel);
    }

    private void initImageSetsPanel() {
        JPanel imageSetsPanel = new JPanel();
        imageSetsPanel.setOpaque(false);
        imageSetsPanel.setBorder(CTBorderFactory.createTitledBorder("图片设置"));
        imageSetsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        CTTextButton downloadButton = new CTTextButton("导入");
        downloadButton.setFont(CTFont.getCTFont(Font.BOLD, CTFontSizeStyle.BIG));
        downloadButton.setIcon("导入", IconControl.COLOR_COLORFUL,
                downloadButton.getFont().getSize(), downloadButton.getFont().getSize());
        downloadButton.addActionListener(e -> {
            try {
                AtomicReference<String> downloadURL = new AtomicReference<>("");
                AtomicReference<String> version = new AtomicReference<>("");
                try {
                    JSONObject jsonObject = new JSONObject(
                            GetWebInf.getWebInf("https://api.github.com/repos/wmp666/ClassTools_Image/releases/latest", false));
                    //判断是否存在
                    version.set(jsonObject.getString("tag_name"));
                    AtomicReference<String> finalDownloadURL = downloadURL;
                    jsonObject.getJSONArray("assets").forEach(object -> {
                        JSONObject asset = (JSONObject) object;
                        if (asset.getString("name").equals("image.zip")) {
                            finalDownloadURL.set(asset.getString("browser_download_url"));
                        }
                    });
                } catch (Exception ex) {
                    Log.warn.message(null, "获取版本号", "图片版本获取失败");
                }
                if (downloadURL.get().isEmpty()) {
                    int i = Log.info.showChooseDialog(this, "询问", "目前无法获取最新版本号,是否需要使用压缩包更新?");
                    if (i == CTOptionPane.YES_OPTION) {
                        version.set(Log.info.showInputDialog(this, "输入图片版本", "请输入图片版本,如:1.0.0"));
                    }
                }

                IconControl.downloadFile(downloadURL, version);
            } catch (Exception ex) {
                Log.err.print(getClass(), "图片下载失败", ex);
            }
        });
        imageSetsPanel.add(downloadButton);

        CTTextButton controlButton = new CTTextButton("管理");
        controlButton.setFont(CTFont.getCTFont(Font.BOLD, CTFontSizeStyle.BIG));
        controlButton.setIcon("设置", IconControl.COLOR_COLORFUL,
                controlButton.getFont().getSize(), controlButton.getFont().getSize());
        controlButton.addActionListener(e -> {
            IconControl.showControlDialog();
        });
        imageSetsPanel.add(controlButton);

        CTTextButton helpButton = new CTTextButton("使用帮助");
        helpButton.setFont(CTFont.getCTFont(Font.BOLD, CTFontSizeStyle.BIG));
        helpButton.addActionListener(e -> ShowHelpDoc.openWebHelpDoc("imageCreativeHelp"));
        imageSetsPanel.add(helpButton);

        this.add(imageSetsPanel);
    }

    private void initMusicSetsPanel() {
        JPanel musicSetsPanel = new JPanel();
        musicSetsPanel.setOpaque(false);
        musicSetsPanel.setBorder(CTBorderFactory.createTitledBorder("音频设置"));
        musicSetsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        CTTextButton downloadButton = new CTTextButton("导入");
        downloadButton.setFont(CTFont.getCTFont(Font.BOLD, CTFontSizeStyle.BIG));
        downloadButton.setIcon("导入", IconControl.COLOR_COLORFUL,
                downloadButton.getFont().getSize(), downloadButton.getFont().getSize());
        downloadButton.addActionListener(e -> {
            try {
                AtomicReference<String> downloadURL = new AtomicReference<>("");
                AtomicReference<String> version = new AtomicReference<>("");

                try {
                    JSONObject jsonObject = new JSONObject(
                            GetWebInf.getWebInf("https://api.github.com/repos/wmp666/ClassTools_Music/releases/latest", false));

                    //判断是否存在
                    version.set(jsonObject.getString("tag_name"));
                    jsonObject.getJSONArray("assets").forEach(object -> {
                        JSONObject asset = (JSONObject) object;
                        if (asset.getString("name").equals("music.zip")) {
                            downloadURL.set(asset.getString("browser_download_url"));
                        }
                    });
                } catch (Exception ex) {
                    Log.warn.message(null, "获取版本号", "音频版本获取失败");
                }
                if (downloadURL.get().isEmpty()) {
                    int i = Log.info.showChooseDialog(this, "询问", "目前无法获取最新版本号,是否需要使用压缩包更新?");
                    if (i == CTOptionPane.YES_OPTION) {
                        version.set(Log.info.showInputDialog(this, "输入音频版本", "请输入音频版本,如:1.0.0"));
                    }
                }
                AudioControl.downloadFile(downloadURL, version);
            } catch (Exception ex) {
                Log.err.print(getClass(), "音频下载失败", ex);
            }
        });
        musicSetsPanel.add(downloadButton);

        CTTextButton controlButton = new CTTextButton("管理");
        controlButton.setFont(CTFont.getCTFont(Font.BOLD, CTFontSizeStyle.BIG));
        controlButton.setIcon("设置", IconControl.COLOR_COLORFUL,
                controlButton.getFont().getSize(), controlButton.getFont().getSize());
        controlButton.addActionListener(e -> {
            AudioControl.showControlDialog();
        });
        musicSetsPanel.add(controlButton);

        CTTextButton helpButton = new CTTextButton("使用帮助");
        helpButton.setFont(CTFont.getCTFont(Font.BOLD, CTFontSizeStyle.BIG));
        helpButton.addActionListener(e -> ShowHelpDoc.openWebHelpDoc("musicCreativeHelp"));
        musicSetsPanel.add(helpButton);

        this.add(musicSetsPanel);
    }

    @Override
    public void save() {

    }

    @Override
    public void refresh() {
        this.removeAll();
        initUI();
        this.revalidate();
        this.repaint();
    }
}
