package com.wmp.publicTools.appFileControl;

import com.wmp.publicTools.CTInfo;
import com.wmp.publicTools.UITools.CTColor;
import com.wmp.publicTools.appFileControl.tools.GetShowTreePanel;
import com.wmp.publicTools.io.DownloadURLFile;
import com.wmp.publicTools.io.GetPath;
import com.wmp.publicTools.io.IOForInfo;
import com.wmp.publicTools.io.ZipPack;
import com.wmp.publicTools.printLog.Log;
import com.wmp.publicTools.update.GetNewerVersion;
import com.wmp.publicTools.web.GetWebInf;
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
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
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

            "屏保.关闭页.关机",

            "天气.晴",
            "天气.少云",
            "天气.晴间多云",
            "天气.多云",
            "天气.阴",
            "天气.有风",
            "天气.平静",
            "天气.微风",
            "天气.和风",
            "天气.清风",
            "天气.强风/劲风",
            "天气.旋风",
            "天气.大风",
            "天气.烈风",
            "天气.风暴",
            "天气.狂暴风",
            "天气.飓风",
            "天气.热带风暴",
            "天气.霜",
            "天气.中度霜",
            "天气.重度霜",
            "天气.严重霜",
            "天气.阵雨",
            "天气.雷阵雨",
            "天气.雷阵雨并伴有冰雹",
            "天气.小雨",
            "天气.中雨",
            "天气.大雨",
            "天气.暴雨",
            "天气.大暴雨",
            "天气.特大暴雨",
            "天气.强阵雨",
            "天气.强雷阵雨",
            "天气.极端降雨",
            "天气.毛毛雨/细雨",
            "天气.雨",
            "天气.小雨-中雨",
            "天气.中雨-大雨",
            "天气.大雨-暴雨",
            "天气.暴雨-大暴雨",
            "天气.大暴雨-特大暴雨",
            "天气.雨雪天气",
            "天气.雨夹雪",
            "天气.阵雨夹雪",
            "天气.冻雨",
            "天气.雪",
            "天气.阵雪",
            "天气.小雪",
            "天气.中雪",
            "天气.大雪",
            "天气.暴雪",
            "天气.小雪-中雪",
            "天气.中雪-大雪",
            "天气.大雪-暴雪",
            "天气.浮尘",
            "天气.扬沙",
            "天气.沙尘暴",
            "天气.强沙尘暴",
            "天气.龙卷风",
            "天气.雾",
            "天气.浓雾",
            "天气.强浓雾",
            "天气.轻雾",
            "天气.大雾",
            "天气.特强浓雾",
            "天气.热",
            "天气.冷",
            "天气.未知"
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

    public static void init(boolean getNewerVersion) {
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
        if (getNewerVersion) {
            try {
                //判断磁盘中是否有图片
                getNewImage();

            } catch (Exception e) {
                Log.warn.print(null, IconControl.class.getName(), "图片数据判断失败:\n" + e);
            }
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
        Map<String, ImageIcon> resultMap = new HashMap<>();

        // 线程列表
        ArrayList<Thread> threads = new ArrayList<>();

        // 缓存图片列表 - 处理前的图片
        ArrayList<Map<String, ImageIcon>> tempImageMap = new ArrayList<>();
        // 处理后的图片
        CopyOnWriteArrayList<Map<String, ImageIcon>> tempResultImageMap = new CopyOnWriteArrayList<>();

        // 将原图按组分割，每组最多包含一定数量的图片
        AtomicReference<HashMap<String, ImageIcon>> temp = new AtomicReference<>(new HashMap<>());
        AtomicInteger count = new AtomicInteger();
        map.forEach((key, value) -> {
            // 每组最多放30张图片
            if (temp.get().size() < 1) {
                temp.get().put(key, value);
            } else {
                // 当前组已满，加入临时列表并新建一个组
                tempImageMap.add(temp.get());
                temp.set(new HashMap<>());
                temp.get().put(key, value); // 将当前图片放入新组
            }
            count.addAndGet(1);
        });

        // 创建虚拟线程并发处理图片
        for (int i = 0; i < tempImageMap.size(); i++) {
            final int index = i;
            threads.add(Thread.ofVirtual()
                    .name("IconControl-Thread-" + i)
                    .unstarted(() -> {
                        try {
                            // 分配任务给每个线程
                            Map<String, ImageIcon> subMap = tempImageMap.get(index);
                            tempResultImageMap.add(ColorImageGenerator.getColorfulImageMap(subMap, color));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }));
        }

        // 等待所有线程完成
        threads.stream()
                .peek(Thread::start)
                .forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Log.trayIcon.displayMessage("IconControl", "线程中断" + e.getMessage(), TrayIcon.MessageType.ERROR);
            }
        });

        // 合并结果
        tempResultImageMap.forEach(resultMap::putAll);

        return resultMap;
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
        Set<String> keySet = new HashSet<>(COLORFUL_IMAGE_MAP.keySet());
        keySet.add("默认");
        String choose = Log.info.showChooseDialog(null, "图标风格", "请选择图标风格", keySet.toArray(new String[0]));

        showIconControlDialog(choose.equals("默认")?
                DEFAULT_IMAGE_MAP:
                COLORFUL_IMAGE_MAP.getOrDefault(choose, DEFAULT_IMAGE_MAP));
    }

    private static void showIconControlDialog(Map<String, ImageIcon> iconMap) {
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

                //ImageIcon icon = getIcon(key.toString(), COLOR_DEFAULT);
                showIconPreviewDialog(key.toString(), iconMap.get(key.toString()));
            }
        });
        controlPanel.add(previewButton);

        HashSet<String> iconKeySet = new HashSet<>(Arrays.asList(ALL_ICON_KEY));
        iconKeySet.addAll(iconMap.keySet());

        JTree showTree = GetShowTreePanel.getShowTreePanel(iconKeySet.toArray(new String[0]), "图标", iconMap);
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

                key.set( sb.toString());
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
        previewDialog.setModal(true); // 设置为非模态，允许返回主窗口
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
