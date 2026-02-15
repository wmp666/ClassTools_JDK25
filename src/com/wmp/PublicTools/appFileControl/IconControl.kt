package com.wmp.publicTools.appFileControl

import com.wmp.classTools.CTComponent.CTBorderFactory
import com.wmp.classTools.CTComponent.CTButton.CTTextButton
import com.wmp.classTools.CTComponent.CTOptionPane
import com.wmp.publicTools.CTInfo
import com.wmp.publicTools.UITools.CTColor
import com.wmp.publicTools.appFileControl.ColorImageGenerator.getColorfulImageMap
import com.wmp.publicTools.appFileControl.tools.GetShowTreePanel
import com.wmp.publicTools.io.DownloadURLFile
import com.wmp.publicTools.io.GetPath
import com.wmp.publicTools.io.IOForInfo
import com.wmp.publicTools.io.IOForInfo.Companion.deleteDirectoryRecursively
import com.wmp.publicTools.io.IOForInfo.Companion.getInfo
import com.wmp.publicTools.io.IOForInfo.Companion.getInfos
import com.wmp.publicTools.io.ZipPack
import com.wmp.publicTools.printLog.Log
import com.wmp.publicTools.update.GetNewerVersion
import com.wmp.publicTools.web.GetWebInf.getWebInf
import org.json.JSONArray
import org.json.JSONObject
import java.awt.*
import java.awt.event.ActionEvent
import java.io.File
import java.io.IOException
import java.net.URL
import java.nio.file.Path
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference
import java.util.function.Consumer
import javax.swing.*
import javax.swing.event.TreeExpansionEvent
import javax.swing.event.TreeExpansionListener
import javax.swing.event.TreeSelectionEvent
import javax.swing.tree.DefaultMutableTreeNode
import kotlin.math.min

object IconControl {
    const val COLOR_DEFAULT: Int = 0
    const val COLOR_COLORFUL: Int = 1

    private val ALL_ICON_KEY = arrayOf<String?>(
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
    )

    private val DEFAULT_IMAGE_MAP: MutableMap<String?, ImageIcon?> = HashMap<String?, ImageIcon?>()
    private val COLORFUL_IMAGE_MAP: MutableMap<String?, MutableMap<String?, ImageIcon?>?> =
        HashMap<String?, MutableMap<String?, ImageIcon?>?>()

    private val ICON_STYLE_MAP: MutableMap<String?, String?> = HashMap<String?, String?>()

    init {
        DEFAULT_IMAGE_MAP["系统.图标"] = ImageIcon(IconControl::class.java.getResource(CTInfo.iconPath))
        COLORFUL_IMAGE_MAP["light"] = DEFAULT_IMAGE_MAP
        COLORFUL_IMAGE_MAP["dark"] = getColorfulImageMap(DEFAULT_IMAGE_MAP, CTColor.getParticularColor("white"))
        COLORFUL_IMAGE_MAP["err"] = getColorfulImageMap(DEFAULT_IMAGE_MAP, CTColor.getParticularColor("blue"))
    }

    fun init(getNewerVersion: Boolean) {
        try {
            DEFAULT_IMAGE_MAP.clear()
            COLORFUL_IMAGE_MAP.clear()

            DEFAULT_IMAGE_MAP["系统.图标"] = ImageIcon(IconControl::class.java.getResource(CTInfo.iconPath))

            //获取基础图标
            val resourceInfos = getInfos(IconControl::class.java.getResource("imagePath.json")!!)
            val resourceJsonArray = JSONArray(resourceInfos)
            resourceJsonArray.forEach(Consumer { `object`: Any? ->
                val jsonObject = `object` as JSONObject
                Log.info.print(
                    "IconControl",
                    String.format("名称:%s|位置:%s", jsonObject.getString("name"), jsonObject.getString("path"))
                )
                val pathStr = jsonObject.getString("path")
                val path = IconControl::class.java.getResource(pathStr)
                if (path == null) {
                    Log.warn.print("IconControl", String.format("图标文件%s不存在", jsonObject.getString("path")))
                    DEFAULT_IMAGE_MAP[jsonObject.getString("name")] =
                        ImageIcon(IconControl::class.java.getResource("/image/optionDialogIcon/warn.png"))
                } else {
                    DEFAULT_IMAGE_MAP[jsonObject.getString("name")] = ImageIcon(path)
                }
                ICON_STYLE_MAP[jsonObject.getString("name")] = jsonObject.getString("style")
            })
        } catch (e: Exception) {
            Log.warn.message(null, IconControl::class.java.getName(), "图片加载失败:\n$e")
        }
        if (getNewerVersion) {
            try {
                //判断磁盘中是否有图片
                newImage
            } catch (e: Exception) {
                Log.warn.print(null, IconControl::class.java.getName(), "图片数据判断失败:\n$e")
            }
        }

        try {
            //获取磁盘中的图标
            val iconInfos = getInfos(CTInfo.APP_INFO_PATH + "image\\imagePath.json")

            val iconJsonArray = JSONArray(iconInfos)
            iconJsonArray.forEach(Consumer { `object`: Any? ->
                val jsonObject = `object` as JSONObject
                Log.info.print(
                    "IconControl",
                    String.format("名称:%s|位置:%s", jsonObject.getString("name"), jsonObject.getString("path"))
                )
                val pathStr = File(CTInfo.APP_INFO_PATH, jsonObject.getString("path")).path
                var path: URL?
                try {
                    val file = File(pathStr)
                    if (!file.exists()) {
                        Log.warn.print("IconControl", String.format("图标文件%s不存在", jsonObject.getString("path")))
                        if (DEFAULT_IMAGE_MAP[jsonObject.getString("name")] == null) {
                            DEFAULT_IMAGE_MAP[jsonObject.getString("name")] =
                                ImageIcon(IconControl::class.java.getResource("/image/optionDialogIcon/warn.png"))
                            ICON_STYLE_MAP[jsonObject.getString("name")] = jsonObject.getString("style")
                        }
                    } else {
                        path = file.toURI().toURL()
                        DEFAULT_IMAGE_MAP[jsonObject.getString("name")] = ImageIcon(path)
                    }
                } catch (_: Exception) {
                    Log.warn.print("IconControl", String.format("图标文件%s不存在", jsonObject.getString("path")))
                    if (DEFAULT_IMAGE_MAP[jsonObject.getString("name")] == null) DEFAULT_IMAGE_MAP[jsonObject.getString("name")] =
                        ImageIcon(IconControl::class.java.getResource("/image/default.png"))
                }
            })
        } catch (e: Exception) {
            Log.warn.message(null, IconControl::class.java.getName(), "本地图片加载失败:\n$e")
        }

        if (!CTInfo.isButtonUseMainColor) {
            COLORFUL_IMAGE_MAP["light"] = DEFAULT_IMAGE_MAP
            COLORFUL_IMAGE_MAP["dark"] = getColorfulImageMap(DEFAULT_IMAGE_MAP, CTColor.getParticularColor("white"))
        } else {
            val colorfulImageMap = getColorfulImageMap(DEFAULT_IMAGE_MAP, CTColor.mainColor)
            COLORFUL_IMAGE_MAP["light"] = colorfulImageMap
            COLORFUL_IMAGE_MAP["dark"] = colorfulImageMap
        }
        COLORFUL_IMAGE_MAP["err"] = getColorfulImageMap(DEFAULT_IMAGE_MAP, CTColor.getParticularColor("blue"))
    }

    @get:Throws(InterruptedException::class)
    private val newImage: Unit
        get() {
            var needDownload = false
            val jsonObject = JSONObject(
                getWebInf("https://api.github.com/repos/wmp666/ClassTools_Image/releases/latest", false)
            )
            val downloadURL = AtomicReference("")
            val version = AtomicReference("")
            val oldVersion = getInfo(CTInfo.APP_INFO_PATH + "image\\version.txt")!![0]
            //判断是否存在
            version.set(jsonObject.getString("tag_name"))
            jsonObject.getJSONArray("assets").forEach(Consumer { `object`: Any? ->
                val asset = `object` as JSONObject
                if (asset.getString("name") == "image.zip") {
                    downloadURL.set(asset.getString("browser_download_url"))
                }
            })

            if (oldVersion != "err") {
                val split: Array<String?> =
                    oldVersion.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val newerVersion = GetNewerVersion.isNewerVersion(version.get()!!, split[split.size - 1]!!)
                if (newerVersion != 0) {
                    if (split[0] != "zip") {
                        Log.info.print("IconControl", "有新图片")
                        needDownload = true
                    } else {
                        val i = Log.warn.showChooseDialog(
                            null,
                            "IconControl",
                            "我们已经更新了官方图片库,而您的图片似乎是使用压缩包导入的(可能为第三方),我们无法确认是否要更新,如果你已经有了相关的最新版本/想要使用官方图片库,请按\"是\",否则按\"否\""
                        )
                        if (i == CTOptionPane.YES_OPTION) {
                            needDownload = true
                        }
                    }
                }
            } else {
                needDownload = true
            }
            if (needDownload) {
                if (!downloadFile(downloadURL, version)) {
                    Log.err.print("IconControl", "图片更新失败")
                }
            }
        }

    @JvmStatic
    @Throws(InterruptedException::class)
    fun downloadFile(downloadURL: AtomicReference<String?>?, version: AtomicReference<String?>): Boolean {
        var choose: String
        if (downloadURL != null && !downloadURL.get()!!.isEmpty()) {
            choose = Log.info.showChooseDialog(
                null,
                "IconControl",
                "图片文件不存在/存在新版,选择获取方式",
                "下载",
                "导入压缩包"
            )
        } else choose = "导入压缩包"
        var zipPath: String?


        when (choose) {
            "下载" -> {
                //下载文件
                val b = DownloadURLFile.downloadWebFile(null, null, downloadURL!!.get(), CTInfo.TEMP_PATH + "appInfo")
                if (!b) return false
                zipPath = CTInfo.TEMP_PATH + "appInfo\\image.zip"
            }
            "导入压缩包" -> {
                Log.warn.message(null, "IconControl", "若导入的图片库为第三方,可能需要自行更新")
                version.set("zip:" + version.get())
                zipPath = GetPath.getFilePath(null, "导入图片", ".zip", "图片压缩包")
            }
            else -> {
                Log.warn.message(null, "IconControl", "若不下载/导入图片,可能造成程序异常")
                return false
            }
        }
        //清空文件
        deleteDirectoryRecursively(Path.of(CTInfo.APP_INFO_PATH + "image"))
        //解压文件
        val thread = ZipPack.unzip(zipPath, CTInfo.APP_INFO_PATH)

        if (thread != null) {
            thread.join()
        }
        //生成版本文件
        try {
            IOForInfo(CTInfo.APP_INFO_PATH + "image\\version.txt").setInfo(version.get()!!)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        return true
    }

    /**
     * 获取彩色图标
     * @param map 原数据
     * @param color 需要更新的颜色
     * @return 修改为目标颜色后的数据
     */
    private fun getColorfulImageMap(
        map: MutableMap<String?, ImageIcon?>,
        color: Color
    ): MutableMap<String?, ImageIcon?> {
        val resultMap: MutableMap<String?, ImageIcon?> = HashMap<String?, ImageIcon?>()

        // 线程列表
        val threads = ArrayList<Thread?>()

        // 缓存图片列表 - 处理前的图片
        val tempImageMap = ArrayList<MutableMap<String?, ImageIcon?>?>()
        // 处理后的图片
        val tempResultImageMap = CopyOnWriteArrayList<MutableMap<String?, ImageIcon?>?>()

        // 将原图按组分割，每组最多包含一定数量的图片
        val temp = AtomicReference(HashMap<String?, ImageIcon?>())
        val count = AtomicInteger()
        map.forEach { (key: String?, value: ImageIcon?) ->
            // 每组最多放30张图片
            if (temp.get()!!.isEmpty()) {
                temp.get()!![key] = value
            } else {
                // 当前组已满，加入临时列表并新建一个组
                tempImageMap.add(temp.get())
                temp.set(HashMap<String?, ImageIcon?>())
                temp.get()!![key] = value // 将当前图片放入新组
            }
            count.addAndGet(1)
        }

        // 创建虚拟线程并发处理图片
        for (i in tempImageMap.indices) {
            threads.add(
                Thread.ofVirtual()
                    .name("IconControl-Thread-$i")
                    .unstarted {
                        try {
                            // 分配任务给每个线程
                            val subMap = tempImageMap[i]
                            tempResultImageMap.add(getColorfulImageMap(subMap, color))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
            )
        }

        // 等待所有线程完成
        threads.stream()
            .peek { obj: Thread? -> obj!!.start() }
            .forEach { thread: Thread? ->
                try {
                    thread!!.join()
                } catch (e: InterruptedException) {
                    Log.trayIcon.displayMessage("IconControl", "线程中断" + e.message, TrayIcon.MessageType.ERROR)
                }
            }

        // 合并结果
        tempResultImageMap.forEach(Consumer { m: MutableMap<String?, ImageIcon?>? -> resultMap.putAll(m!!) })

        return resultMap
    }


    @JvmStatic
    fun getIconStyle(name: String?): String? {
        return ICON_STYLE_MAP.getOrDefault(name, "png")
    }

    fun getDefaultIcon(name: String?): ImageIcon? {
        return DEFAULT_IMAGE_MAP.getOrDefault(
            name,
            DEFAULT_IMAGE_MAP["default"]
        )
    }

    fun getColorfulIcon(name: String?): ImageIcon? {
        val defaultMap = HashMap<String?, ImageIcon?>()
        defaultMap["default"] = DEFAULT_IMAGE_MAP["default"]
        return COLORFUL_IMAGE_MAP.getOrDefault(CTColor.style, defaultMap)!!
            .getOrDefault(name, DEFAULT_IMAGE_MAP["default"])
    }


    @JvmStatic
    fun getIcon(name: String?, colorStyle: Int): ImageIcon {
        val imageIcon = if (colorStyle == COLOR_DEFAULT) getDefaultIcon(name) else getColorfulIcon(name)
        if (imageIcon == null) {
            return ImageIcon(IconControl::class.java.getResource("/image/default.png"))
        }
        return imageIcon
    }

    @JvmStatic
    fun showControlDialog() {
        val keySet: MutableSet<String?> = HashSet<String?>(COLORFUL_IMAGE_MAP.keys)
        keySet.add("默认")
        val choose = Log.info.showChooseDialog(null, "图标风格", "请选择图标风格", *keySet.toTypedArray<String?>())

        showIconControlDialog(
            (if (choose == "默认") DEFAULT_IMAGE_MAP else COLORFUL_IMAGE_MAP.getOrDefault(
                choose,
                DEFAULT_IMAGE_MAP
            ))!!
        )
    }

    private fun showIconControlDialog(iconMap: MutableMap<String?, ImageIcon?>) {
        val controlDialog: JDialog = object : JDialog() {
            override fun pack() {
                val preferredSize = super.getPreferredSize()
                val screenSize = Toolkit.getDefaultToolkit().screenSize
                preferredSize.height = min(preferredSize.height.toDouble(), screenSize.height * 0.75).toInt()
                preferredSize.width = min(preferredSize.width.toDouble(), screenSize.width * 0.5).toInt()
                super.setSize(preferredSize)
            }
        }
        controlDialog.setTitle("图标控制")
        controlDialog.setModal(true)
        controlDialog.contentPane.setLayout(BorderLayout())

        val controlPanel = JPanel()
        controlPanel.setLayout(FlowLayout(FlowLayout.LEFT, 10, 10))
        controlPanel.setOpaque(false)
        controlPanel.setBorder(CTBorderFactory.createTitledBorder("图标预览"))

        val iconInfo = JLabel("请选择图标")
        controlPanel.add(iconInfo)

        val key = AtomicReference("")
        // 添加预览按钮
        val previewButton = CTTextButton("预览")
        previewButton.setEnabled(false) // 初始时禁用，当选中图标后启用
        previewButton.addActionListener { _: ActionEvent? ->
            // 获取当前选中的图标
            if (key.get() != null) {
                // 获取图标并显示在新窗口中

                //ImageIcon icon = getIcon(key.toString(), COLOR_DEFAULT);


                showIconPreviewDialog(key.toString(), iconMap[key.toString()]!!)
            }
        }
        controlPanel.add(previewButton)

        val iconKeySet = HashSet(listOf(*ALL_ICON_KEY))
        iconKeySet.addAll(iconMap.keys)

        val showTree = GetShowTreePanel.getShowTreePanel(iconKeySet.toTypedArray<String?>(), "图标", iconMap)
        showTree.addTreeExpansionListener(object : TreeExpansionListener {
            override fun treeExpanded(event: TreeExpansionEvent?) {
                controlDialog.pack()
            }

            override fun treeCollapsed(event: TreeExpansionEvent?) {
                controlDialog.pack()
            }
        })
        showTree.addTreeSelectionListener { e: TreeSelectionEvent? ->
            if (showTree.getLastSelectedPathComponent() != null &&
                showTree.getLastSelectedPathComponent() is DefaultMutableTreeNode
            ) {
                //获取Key
                val path = e!!.path.getPath()
                val sb = StringBuilder()
                for (i in 1..<path.size) {
                    sb.append(path[i])
                    if (i != path.size - 1) {
                        sb.append(".")
                    }
                }

                key.set(sb.toString())
                iconInfo.setText(sb.toString())
                previewButton.setEnabled(true) // 选中图标后启用预览按钮

                controlDialog.pack()
                controlDialog.repaint()
            }
        }

        val showPanel = JPanel()
        showPanel.setLayout(BorderLayout())
        showPanel.setBorder(CTBorderFactory.createTitledBorder("图标列表"))
        showPanel.add(showTree)

        controlDialog.contentPane.add(JScrollPane(showPanel), BorderLayout.CENTER)
        controlDialog.contentPane.add(JScrollPane(controlPanel), BorderLayout.SOUTH)

        controlDialog.pack()
        controlDialog.setLocationRelativeTo(null)
        controlDialog.isVisible = true
    }

    /**
     * 显示图标预览弹窗
     * @param iconName 图标名称
     * @param icon 要预览的图标
     */
    private fun showIconPreviewDialog(iconName: String?, icon: ImageIcon) {
        val previewDialog = JDialog()
        previewDialog.setTitle("预览图标 - $iconName")
        previewDialog.setAlwaysOnTop(true)
        previewDialog.setModal(true) // 设置为非模态，允许返回主窗口
        previewDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE)

        // 创建标签显示图标，保持原始比例
        val iconLabel = JLabel(icon)
        iconLabel.setHorizontalAlignment(JLabel.CENTER)

        // 创建滚动面板以支持大图标
        val scrollPane = JScrollPane(iconLabel)
        scrollPane.preferredSize = Dimension(
            min(icon.iconWidth + 20, 800),
            min(icon.iconHeight + 50, 600)
        )

        previewDialog.add(scrollPane)
        previewDialog.pack()
        previewDialog.setLocationRelativeTo(null)
        previewDialog.isVisible = true
    }
}
