package com.wmp.PublicTools.appFileControl;

import com.wmp.PublicTools.CTInfo;
import com.wmp.PublicTools.UITools.CTColor;
import com.wmp.PublicTools.UITools.ColorConverter;
import com.wmp.PublicTools.appFileControl.tools.GetShowTreePanel;
import com.wmp.PublicTools.io.DownloadURLFile;
import com.wmp.PublicTools.io.GetPath;
import com.wmp.PublicTools.io.IOForInfo;
import com.wmp.PublicTools.io.ZipPack;
import com.wmp.PublicTools.printLog.Log;
import com.wmp.PublicTools.update.GetNewerVersion;
import com.wmp.PublicTools.web.GetWebInf;
import com.wmp.classTools.CTComponent.CTBorderFactory;
import com.wmp.classTools.CTComponent.CTButton.CTTextButton;
import com.wmp.classTools.CTComponent.CTOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class IconControl {
    public static final int COLOR_DEFAULT = 0;
    public static final int COLOR_COLORFUL = 1;

    private static final String[] ALL_ICON_KEY = {
            "关于.哔哩哔哩",
            "关于.Github",
            "关于.QQ",
            "关于.微信",

            "系统.关闭页.明天见",
            "系统.关闭页.我们终将重逢",
            "系统.关闭页.愿此行，终抵群星",
            "系统.关闭页.为了与你重逢愿倾尽所有",
            "系统.关闭页.<html>生命从夜中醒来<br>却在触碰到光明的瞬间坠入永眠</html>",
            "系统.关闭页.一起走向明天，我们不曾分离",

            "通用.保存",

            "通用.网络.更新",
            "通用.网络.下载",

            "通用.设置",
            "通用.快速启动",
            "通用.快捷工具",
            "通用.刷新",
            "通用.关于",
            "通用.日志",
            "通用.关闭",
            "通用.更多",
            "通用.添加",
            "通用.删除",

            "值日表.上一天",
            "值日表.下一天",

            "通用.文件.文件夹",
            "通用.文件.导入",
            "通用.文件.导出",

            "通用.祈愿",
            "通用.编辑",
            "通用.文档",
            "通用.进度",

            "屏保.关闭页.关机"
    };

    private static final Map<String, ImageIcon> DEFAULT_IMAGE_MAP = new HashMap<>();
    private static final Map<String, Map<String, ImageIcon>> COLORFUL_IMAGE_MAP = new HashMap<>();

    private static final Map<String, String> ICON_STYLE_MAP = new HashMap<>();

    static{
        DEFAULT_IMAGE_MAP.put("系统.图标", new ImageIcon(IconControl.class.getResource(CTInfo.iconPath)));
        COLORFUL_IMAGE_MAP.put("light", DEFAULT_IMAGE_MAP);
        COLORFUL_IMAGE_MAP.put("dark",
                getColorfulImageMap(DEFAULT_IMAGE_MAP, CTColor.getParticularColor("white")));
        COLORFUL_IMAGE_MAP.put("err",
                getColorfulImageMap(DEFAULT_IMAGE_MAP, CTColor.getParticularColor("blue")));
    }

    public static void init() {
        try {
            DEFAULT_IMAGE_MAP.clear();
            COLORFUL_IMAGE_MAP.clear();

            DEFAULT_IMAGE_MAP.put("系统.图标", new ImageIcon(IconControl.class.getResource(CTInfo.iconPath)));

            //获取基础图标
            String resourceInfos = IOForInfo.getInfos(IconControl.class.getResource("imagePath.json"));
            JSONArray resourceJsonArray = new JSONArray(resourceInfos);
            resourceJsonArray.forEach(object -> {

                JSONObject jsonObject = (JSONObject) object;
                Log.info.print("IconControl", String.format("名称:%s|位置:%s", jsonObject.getString("name"), jsonObject.getString("path")));
                String pathStr = jsonObject.getString("path");
                URL path = IconControl.class.getResource(pathStr);
                if (path == null) {
                    Log.warn.print("IconControl", String.format("图标文件%s不存在", jsonObject.getString("path")));
                    DEFAULT_IMAGE_MAP.put(jsonObject.getString("name"),
                            new ImageIcon(IconControl.class.getResource("/image/optionDialogIcon/warn.png")));
                } else {
                    DEFAULT_IMAGE_MAP.put(jsonObject.getString("name"),
                            new ImageIcon(path));
                }
                ICON_STYLE_MAP.put(jsonObject.getString("name"), jsonObject.getString("style"));
            });


        } catch (Exception e) {
            Log.warn.message(null, IconControl.class.getName(), "图片加载失败:\n" + e);
        }
        try {
            //判断磁盘中是否有图片
            getNewImage();

        } catch (Exception e) {
            Log.warn.print(null, IconControl.class.getName(), "图片数据判断失败:\n" + e);
        }

        try {
            //获取磁盘中的图标
            String iconInfos = IOForInfo.getInfos(CTInfo.APP_INFO_PATH + "image\\imagePath.json");

            JSONArray iconJsonArray = new JSONArray(iconInfos);
            iconJsonArray.forEach(object -> {
                JSONObject jsonObject = (JSONObject) object;
                Log.info.print("IconControl", String.format("名称:%s|位置:%s", jsonObject.getString("name"), jsonObject.getString("path")));
                String pathStr = new File(CTInfo.APP_INFO_PATH, jsonObject.getString("path")).getPath();
                URL path = null;
                try {
                    File file = new File(pathStr);
                    if (!file.exists()) {
                        Log.warn.print("IconControl", String.format("图标文件%s不存在", jsonObject.getString("path")));
                        if (DEFAULT_IMAGE_MAP.get(jsonObject.getString("name")) == null) {
                            DEFAULT_IMAGE_MAP.put(jsonObject.getString("name"),
                                    new ImageIcon(IconControl.class.getResource("/image/optionDialogIcon/warn.png")));
                            ICON_STYLE_MAP.put(jsonObject.getString("name"), jsonObject.getString("style"));
                        }
                    } else {
                        path = file.toURI().toURL();
                        DEFAULT_IMAGE_MAP.put(jsonObject.getString("name"),
                                new ImageIcon(path));
                    }

                } catch (MalformedURLException e) {
                    Log.warn.print("IconControl", String.format("图标文件%s不存在", jsonObject.getString("path")));
                    if (DEFAULT_IMAGE_MAP.get(jsonObject.getString("name")) == null)
                        DEFAULT_IMAGE_MAP.put(jsonObject.getString("name"),
                                new ImageIcon(IconControl.class.getResource("/image/default.png")));
                }
            });

        } catch (Exception e) {
            Log.warn.message(null, IconControl.class.getName(), "本地图片加载失败:\n" + e);
        }

        if (!CTInfo.isButtonUseMainColor) {
            COLORFUL_IMAGE_MAP.put("light", DEFAULT_IMAGE_MAP);
            COLORFUL_IMAGE_MAP.put("dark",
                    getColorfulImageMap(DEFAULT_IMAGE_MAP, CTColor.getParticularColor("white")));

        }else{
            Map<String, ImageIcon> colorfulImageMap = getColorfulImageMap(DEFAULT_IMAGE_MAP, CTColor.mainColor);
            COLORFUL_IMAGE_MAP.put("light", colorfulImageMap);
            COLORFUL_IMAGE_MAP.put("dark", colorfulImageMap);
        }
        COLORFUL_IMAGE_MAP.put("err",
                getColorfulImageMap(DEFAULT_IMAGE_MAP, CTColor.getParticularColor("blue")));
    }

    private static void getNewImage() throws InterruptedException {

            boolean needDownload = false;
            JSONObject jsonObject = new JSONObject(
                    GetWebInf.getWebInf("https://api.github.com/repos/wmp666/ClassTools_Image/releases/latest", false));
            AtomicReference<String> downloadURL = new AtomicReference<>("");
            AtomicReference<String> version = new AtomicReference<>("");
            String oldVersion = IOForInfo.getInfo(CTInfo.APP_INFO_PATH + "image\\version.txt")[0];
            //判断是否存在
            version.set(jsonObject.getString("tag_name"));
            jsonObject.getJSONArray("assets").forEach(object -> {
                JSONObject asset = (JSONObject) object;
                if (asset.getString("name").equals("image.zip")) {
                    downloadURL.set(asset.getString("browser_download_url"));
                }
            });

            if (!oldVersion.equals("err")) {
                String[] split = oldVersion.split(":");
                int newerVersion = GetNewerVersion.isNewerVersion(version.get(), split[split.length - 1]);
                if (newerVersion != 0) {
                    if (!split[0].equals("zip")) {
                        Log.info.print("IconControl", "有新图片");
                        needDownload = true;
                    } else {
                        int i = Log.warn.showChooseDialog(null, "IconControl", "我们已经更新了官方图片库,而您的图片似乎是使用压缩包导入的(可能为第三方),我们无法确认是否要更新,如果你已经有了相关的最新版本/想要使用官方图片库,请按\"是\",否则按\"否\"");
                        if (i == CTOptionPane.YES_OPTION) {
                            needDownload = true;
                        }
                    }
                }


            } else {
                needDownload = true;
            }
            if (needDownload) {
                if (!downloadFile(downloadURL, version)) {
                    Log.err.print("IconControl", "图片更新失败");
                }
            }
    }

    public static boolean downloadFile(AtomicReference<String> downloadURL, AtomicReference<String> version) throws InterruptedException {
        String choose = "";
        if (downloadURL != null && !downloadURL.get().isEmpty()) {
            choose = Log.info.showChooseDialog(null, "IconControl", "图片文件不存在/存在新版,选择获取方式", "下载", "导入压缩包");
        }else choose = "导入压缩包";
        String zipPath = "";


        if (choose.equals("下载")) {
            //下载文件
            boolean b = DownloadURLFile.downloadWebFile(null, null, downloadURL.get(), CTInfo.TEMP_PATH + "appInfo");
            if (!b) return false;
            zipPath = CTInfo.TEMP_PATH + "appInfo\\image.zip";

        } else if (choose.equals("导入压缩包")) {
            Log.warn.message(null, "IconControl", "若导入的图片库为第三方,可能需要自行更新");
            version.set("zip:" + version.get());
            zipPath = GetPath.getFilePath(null, "导入图片", ".zip", "图片压缩包");
        } else {
            Log.warn.message(null, "IconControl", "若不下载/导入图片,可能造成程序异常");
            return false;
        }
        //清空文件
        IOForInfo.deleteDirectoryRecursively(Path.of(CTInfo.APP_INFO_PATH + "image"));
        //解压文件
        Thread thread = ZipPack.unzip(zipPath, CTInfo.APP_INFO_PATH);

        if (thread != null) {
            thread.join();
        }
        //生成版本文件
        try {
            new IOForInfo(CTInfo.APP_INFO_PATH + "image\\version.txt").setInfo(version.get());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * 获取彩色图标
     * @param map 原数据
     * @param color 需要更新的颜色
     * @return 修改为目标颜色后的数据
     */
    private static Map<String, ImageIcon> getColorfulImageMap(Map<String, ImageIcon> map, Color color) {
        Map<String, ImageIcon> colorfulImageMap = new HashMap<>();
        map.forEach((name, imageIcon) -> {
            BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.drawImage(imageIcon.getImage(), 0, 0, null);

            ImageIcon icon = new ImageIcon(ColorConverter.applyColorTone(
                    ColorConverter.convertToGrayscale(bufferedImage),
                    color.getRed(), color.getGreen(), color.getBlue()));

            colorfulImageMap.put(name, icon);
        });
        return colorfulImageMap;
    }

    public static String getIconStyle(String name) {
        return ICON_STYLE_MAP.getOrDefault(name, "png");
    }

    public static ImageIcon getDefaultIcon(String name) {
        return DEFAULT_IMAGE_MAP.getOrDefault(name,
                DEFAULT_IMAGE_MAP.get("default"));
    }

    public static ImageIcon getColorfulIcon(String name) {

        HashMap<String, ImageIcon> defaultMap = new HashMap<>();
        defaultMap.put("default", DEFAULT_IMAGE_MAP.get("default"));
        return COLORFUL_IMAGE_MAP.getOrDefault(CTColor.style, defaultMap)
                .getOrDefault(name, DEFAULT_IMAGE_MAP.get("default"));
    }


    public static ImageIcon getIcon(String name, int colorStyle) {
        ImageIcon imageIcon = colorStyle == COLOR_DEFAULT ? getDefaultIcon(name) : getColorfulIcon(name);
        if (imageIcon == null) {
            return new ImageIcon(IconControl.class.getResource("/image/default.png"));
        }
        return imageIcon;
    }

    public static void showControlDialog(){
        JDialog controlDialog = new JDialog(){
            @Override
            public void pack() {
                Dimension preferredSize = super.getPreferredSize();
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                preferredSize.height = (int) Math.min(preferredSize.height, screenSize.height * 0.75);
                preferredSize.width = (int) Math.min(preferredSize.width, screenSize.width * 0.5);
                super.setSize(preferredSize);
            }
        };
        controlDialog.setTitle("图标控制");
        controlDialog.setModal(true);
        controlDialog.getContentPane().setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        controlPanel.setOpaque(false);
        controlPanel.setBorder(CTBorderFactory.createTitledBorder("图标预览"));

        JLabel selectIcon = new JLabel();
        selectIcon.setPreferredSize(new Dimension(32, 32)); // 设置图标显示区域大小
        controlPanel.add(selectIcon);

        JLabel iconInfo = new JLabel("请选择图标");
        controlPanel.add(iconInfo);

        AtomicReference<String> key= new AtomicReference<>("");
        // 添加预览按钮
        CTTextButton previewButton = new CTTextButton("预览");
        previewButton.setEnabled(false); // 初始时禁用，当选中图标后启用
        previewButton.addActionListener(e -> {
            // 获取当前选中的图标
            if (key.get() != null) {


                // 获取图标并显示在新窗口中
                ImageIcon icon = getIcon(key.toString(), COLOR_DEFAULT);
                showIconPreviewDialog(key.toString(), icon);
            }
        });
        controlPanel.add(previewButton);

        JTree showTree = GetShowTreePanel.getShowTreePanel(ALL_ICON_KEY, "图标");
        showTree.addTreeExpansionListener(new TreeExpansionListener() {
            @Override
            public void treeExpanded(TreeExpansionEvent event) {
                controlDialog.pack();
            }

            @Override
            public void treeCollapsed(TreeExpansionEvent event) {
                controlDialog.pack();
            }
        });
        showTree.addTreeSelectionListener(e-> {
            if(showTree.getLastSelectedPathComponent() != null &&
                    showTree.getLastSelectedPathComponent() instanceof DefaultMutableTreeNode){
                //获取Key
                Object[] path = e.getPath().getPath();
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i < path.length; i++) {
                    sb.append(path[i]);
                    if(i != path.length-1){
                        sb.append(".");
                    }
                }

                ImageIcon icon = getIcon(sb.toString(), COLOR_DEFAULT);
                key.set( sb.toString());
                selectIcon.setIcon(icon);
                iconInfo.setText(sb.toString());
                previewButton.setEnabled(true); // 选中图标后启用预览按钮

                controlDialog.pack();
                controlDialog.repaint();
            }
        });

        JPanel showPanel = new JPanel();
        showPanel.setLayout(new BorderLayout());
        showPanel.setBorder(CTBorderFactory.createTitledBorder("图标列表"));
        showPanel.add(showTree);

        controlDialog.getContentPane().add(new JScrollPane(showPanel), BorderLayout.CENTER);
        controlDialog.getContentPane().add(new JScrollPane(controlPanel), BorderLayout.SOUTH);

        controlDialog.pack();
        controlDialog.setLocationRelativeTo(null);
        controlDialog.setVisible(true);
    }

    /**
     * 显示图标预览弹窗
     * @param iconName 图标名称
     * @param icon 要预览的图标
     */
    private static void showIconPreviewDialog(String iconName, ImageIcon icon) {
        JDialog previewDialog = new JDialog();
        previewDialog.setTitle("预览图标 - " + iconName);
        previewDialog.setAlwaysOnTop(true);
        previewDialog.setModal(false); // 设置为非模态，允许返回主窗口
        previewDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // 创建标签显示图标，保持原始比例
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setHorizontalAlignment(JLabel.CENTER);

        // 创建滚动面板以支持大图标
        JScrollPane scrollPane = new JScrollPane(iconLabel);
        scrollPane.setPreferredSize(new Dimension(
                Math.min(icon.getIconWidth() + 20, 800),
                Math.min(icon.getIconHeight() + 50, 600)
        ));

        previewDialog.add(scrollPane);
        previewDialog.pack();
        previewDialog.setLocationRelativeTo(null);
        previewDialog.setVisible(true);
    }


}
