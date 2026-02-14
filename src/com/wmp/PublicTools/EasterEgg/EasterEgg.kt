package com.wmp.publicTools.EasterEgg

import com.wmp.Main
import com.wmp.Main.isHasTheArg
import com.wmp.classTools.CTComponent.CTOptionPane
import com.wmp.classTools.SwingRun
import com.wmp.publicTools.CTInfo
import com.wmp.publicTools.DateTools
import com.wmp.publicTools.UITools.GetIcon
import com.wmp.publicTools.appFileControl.IconControl
import com.wmp.publicTools.io.GetPath
import com.wmp.publicTools.io.IOForInfo
import com.wmp.publicTools.io.ResourceLocalizer
import com.wmp.publicTools.printLog.Log
import com.wmp.publicTools.videoView.MediaPlayer
import com.wmp.publicTools.web.GetWebInf.getWebInf
import org.json.JSONArray
import org.json.JSONObject
import java.awt.Color
import java.io.File
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference
import java.util.function.Consumer
import javax.swing.SwingWorker

object EasterEgg {
    const val STYLE_EE_VIDEO: Int = 0
    const val STYLE_EE_MUSIC: Int = 1
    const val STYLE_EE_OTHER: Int = 2

    val easterEggItem: EasterEggModeMap
        get() {
            val errMode = EasterEggModeMap(
                "999.999.999", "银狼", "班级病毒",
                "/image/err/icon.png", "骇客已入侵", true,
                Color(246, 250, 255), Color(0x29A8E3), Color(0x29A8E3), "err",
                false, Runnable {
                    Log.info.loading.updateDialog("窗口加载", "骇客已强制介入加载过程")
                    Log.err.systemPrint(
                        SwingRun::class.java,
                        "程序加载出错!",
                        Exception("加载异常")
                    )

                    Log.info.loading.showDialog("修复", "正在启动修复程序...")

                    try {
                        Log.info.loading.updateDialog("修复", "开始扫描程序文件...")
                        run {
                            val count = AtomicInteger(0)
                            val appPath = Path.of(GetPath.getAppPath(GetPath.APPLICATION_PATH))
                            val fileCount = Files.walk(appPath)
                                .count()
                            if (fileCount > 0) {
                                Files.walk(appPath)
                                    .sorted()
                                    .map { obj: Path? -> obj!!.toFile() }
                                    .forEach { _ ->
                                        val currentCount = count.incrementAndGet()
                                        val percentage = (currentCount * 100.0) / fileCount
                                        val progress = Math.round(percentage).toInt()
                                        Log.info.loading.updateDialog(
                                            "修复",
                                            String.format("开始扫描程序文件%.2f%%", percentage),
                                            progress
                                        )
                                    }
                            }
                        }

                        Log.info.loading.updateDialog("修复", "正在扫描数据文件...")
                        run {
                            val count = AtomicInteger(0)
                            val dataPath = Path.of(CTInfo.TEMP_PATH).getParent()
                            val fileCount = Files.walk(dataPath)
                                .count()
                            if (fileCount > 0) {
                                Files.walk(dataPath)
                                    .sorted()
                                    .map<File?> { obj: Path? -> obj!!.toFile() }
                                    .forEach { file: File? ->
                                        val currentCount = count.incrementAndGet()
                                        val percentage = (currentCount * 100.0) / fileCount
                                        val progress = Math.round(percentage).toInt()
                                        Log.info.loading.updateDialog(
                                            "修复",
                                            String.format("开始扫描数据文件%.2f%%", percentage),
                                            progress
                                        )
                                    }
                            }
                        }
                    } catch (`_`: Exception) {
                    }

                    Log.info.loading.updateDialog("修复", "正在修复文件...", -1)
                    run {
                        try {
                            Thread.sleep(2000)
                        } catch (`_`: InterruptedException) {
                        }
                        Log.info.loading.updateDialog("修复", "修复出错!")
                        Log.err.systemPrint(
                            SwingRun::class.java,
                            "修复出错",
                            Exception("Silver Wolf强制截停修复进程")
                        )
                    }

                    Log.info.loading.updateDialog("修复", "修复出错,正在准备关闭程序", -1)
                    Log.err.systemPrint(
                        SwingRun::class.java,
                        "骇客已入侵",
                        Exception("关闭程序时出现错误,无法修复")
                    )

                    Log.info.loading.showDialog("骇客已入侵", "正在修改修复程序")
                    run {
                        try {
                            Thread.sleep(3000)
                        } catch (`_`: InterruptedException) {
                        }
                        Log.info.loading.updateDialog("骇客已入侵", "已修改修复程序")
                        Log.info.loading.closeDialog("骇客已入侵")
                    }

                    Log.info.loading.updateDialog("修复", "修复成功!正在启动程序", -1)
                    run {
                        for (i in 0..99) {
                            Log.info.loading.updateDialog("修复", i)
                            try {
                                Thread.sleep(5)
                            } catch (`_`: InterruptedException) {
                            }
                        }
                    }

                    Log.info.loading.closeDialog("修复")
                    Log.info.systemPrint("骇客已入侵", "这次能让我玩得开心点么？")
                })
            errMode.addMore(
                EasterEggPair(
                    "加载文字集", arrayOf<String>(
                        "骇客已入侵:\\n游戏就只是为了游戏\\n仅此而已！",
                        "骇客已入侵:\\n重要的不是数值\\n是体验，是操作！",
                        "骇客已入侵:\\n这次能让我玩得开心点么？"
                    )
                )
            )


            //强制启动骇客入侵模式
            if (isHasTheArg("CTInfo:isError")) return errMode

            if (isHasTheArg("EasterEgg:notShow")) return CTInfo.easterEggModeMap

            //铭记

            //祖国万岁
            if (DateTools.dayIsNow("09-18") ||
                DateTools.dayIsNow("10-01") ||
                DateTools.dayIsNow("05-01") ||
                DateTools.dayIsNow("12-26")
            ) {
                return EasterEggModeMap(
                    "999.10.01", "中国人民", "中华人民共和国",
                    "/image/icon/icon_red.png", "祖国万岁", true,
                    Color(255, 214, 214), Color.RED, Color.RED, "light",
                    true, Runnable {
                        CTOptionPane.showFullScreenMessageDialog("祖国万岁", "中华人民共和国万岁!", 60, 1)
                    })
            }

            //骇客入侵
            run {
                val b = DateTools.dayIsNow("04-07")
                if (!b) {
                    if (DateTools.dayIsNow("04-25")) { //崩铁
                        val r = Random()
                        if (r.nextInt(5) == 0) {
                            return errMode
                        }
                    }
                    val r = Random()
                    if (r.nextInt(20) == 0) {
                        return errMode
                    }
                }
            }

            //生日
            run {
                // 茜特菈莉
                if (DateTools.dayIsNow("01-20")) {
                    return EasterEggModeMap(
                        "999.01.20", "茜特菈莉", "烟谜主",
                        "/image/err/xtll.png", "茜特菈莉", true,
                        Color(217, 208, 229), Color(0x9A93DD), Color(0x6F65C7), "light",
                        true, Runnable {
                            CTOptionPane.showFullScreenMessageDialog(CTInfo.appName, "今天是...?", 3, 1)
                        })
                }
                //温迪
                if (DateTools.dayIsNow("06-16")) {
                    return EasterEggModeMap(
                        "999.06.16", "温迪", "蒙德",
                        "/image/err/xtll.png", "温迪", true,
                        Color(230, 255, 221), Color(0x05E666), Color(0x05E666), "light",
                        true, Runnable {
                            CTOptionPane.showFullScreenMessageDialog(CTInfo.appName, "今天是...?", 3, 1)
                        })
                }
                //散兵
                if (DateTools.dayIsNow("01-03")) {
                    return EasterEggModeMap(
                        "999.01.03", "散兵", "稻妻",
                        "/image/err/sanbing.png", "散兵", true,
                        Color(230, 255, 221), Color(0x29A5E3), Color(0x29A5E3), "light",
                        true, Runnable {
                            CTOptionPane.showFullScreenMessageDialog(CTInfo.appName, "今天是...?", 3, 1)
                        })
                }
                //哥伦比娅
                if (DateTools.dayIsNow("01-14")) {
                    return EasterEggModeMap(
                        "999.01.14", "哥伦比娅•希泊塞莱尼娅", "新月",
                        "/image/err/yueshen.jpg", "空月", true,
                        Color(230, 255, 221), Color(0x29A5E3), Color(0x29A5E3), "light",
                        true, Runnable {
                            CTOptionPane.showFullScreenMessageDialog(
                                CTInfo.appName,
                                "你可以叫我哥伦比娅，也可以叫我库塔尔、月神大人...选你喜欢的吧。我习惯了有很多名字的日子。 若你需要的话，我会给予你月亮的赐福。",
                                3,
                                1
                            )
                        })
                }
                //这位是？
                if (DateTools.dayIsNow("10-11")) {
                    val map = EasterEggModeMap(
                        "999.10.11", "ᝰꫛ", "ᝰꫛ",
                        "/image/err/lcl.jpg", "ᝰꫛ", true,
                        Color(230, 255, 221), Color(0x29A5E3), Color(0x29A5E3), "light",
                        true, Runnable {
                            CTOptionPane.showFullScreenMessageDialog(
                                CTInfo.appName,
                                "个签：\n风很温柔 花很浪漫 你很特别 我喜欢你.",
                                3,
                                1
                            )
                        })
                    map.addMore(
                        EasterEggPair(
                            "加载文字集", arrayOf<String>(
                                "墨之尽头\n——刘CL",
                                "我把爱意藏在名字里, \n那是我难以言说的秘密\n——刘CL",
                                "是绝密航天风太大, 还是你听不懂我讲话——刘CL",
                                "机密大坝一片天， 谁见良爷不递烟——刘CL"
                            )
                        ), EasterEggPair(
                            "关闭文字集", arrayOf<String>(
                                "墨之尽头\n——刘CL",
                                "我把爱意藏在名字里, \n那是我难以言说的秘密\n——刘CL",
                                "是绝密航天风太大, \n还是你听不懂我讲话——刘CL",
                                "机密大坝一片天， \n谁见良爷不递烟——刘CL",
                                "风很温柔\n花很浪漫\n你很特别\n我喜欢你"
                            )
                        )
                    )
                    return map
                }
            }


            //一些较特殊的纪念日
            if (DateTools.dayIsNow("09-28") ||  //原神周年庆
                DateTools.dayIsNow("lunar9-17") ||  //author birthday
                DateTools.dayIsNow("09-03") ||  //mc
                DateTools.dayIsNow("04-25")
            ) { //崩铁
                return EasterEggModeMap(
                    "999.999.999", "彩蛋", "班级■■",
                    "/image/icon/icon_red.png", "欸嘿", true,
                    Color(230, 255, 221), Color(0x05E666), Color(0x05E666), "light",
                    true, Runnable {
                        CTOptionPane.showFullScreenMessageDialog("欸嘿", "欸嘿", 3, 1)
                    })
            }

            //新年
            if (DateTools.dayIsNow("12-31") ||
                DateTools.dayIsNow("01-01") ||
                DateTools.dayIsNow("01-02") ||
                (DateTools.getRemainderDay("lunar01-15") <= 30)
            ) {
                return EasterEggModeMap(
                    "1.0.0", "刘德华", "恭喜发财",
                    "/image/icon/icon_red.png", "恭喜发财", true,
                    Color(255, 242, 242), Color.RED, Color.RED, "light",
                    true, Runnable {}, "版本"
                )
            }

            //愚人节
            val b = DateTools.dayIsNow("04-01")
            if (b) {
                return EasterEggModeMap(
                    "999.999.999", "彩蛋", "班级■■",
                    "/image/err/icon.png", "欸嘿", true,
                    Color(230, 255, 221), Color(0x05E666), Color(0x05E666), "light",
                    true, Runnable {
                        CTOptionPane.showFullScreenMessageDialog("欸嘿", "欸嘿", 3, 1)
                    })
            }

            return CTInfo.easterEggModeMap
        }

    @JvmStatic
    fun getPin() {
            val style = Log.info.showChooseDialog(
                null,
                "祈愿",
                "请输入选择彩蛋格式\n注:\"其他\"指不是常规格式(MP3, MP4)的文件",
                "视频",
                "音乐",
                "其他"
            )

            Thread{
                Log.info.print(
                    "彩蛋",
                    "正在获取数据,稍安勿躁..."
                )
            }.start()
            try {
                val info = AtomicReference<JSONArray?>(JSONArray())

                //name, key
                val keyMap = HashMap<String?, String?>()
                //name, URL
                val musicMap = HashMap<String?, String?>()
                val videoMap = HashMap<String?, String?>()
                val otherMap = HashMap<String?, String?>()

                //获取彩蛋列表
                val webInf =
                    getWebInf("https://api.github.com/repos/wmp666/ClassTools/releases/tags/0.0.1")
                val jsonObject = JSONObject(webInf)

                val assets = jsonObject.getJSONArray("assets")
                assets.forEach(Consumer { asset: Any? ->
                    if (asset is JSONObject) {
                        val name = asset.getString("name")
                        val browser_download_url = asset.getString("browser_download_url")

                        keyMap.put(name, name)

                        if (name.endsWith(".mp3") ||
                            name.endsWith(".flac")
                        ) {
                            musicMap.put(name, browser_download_url)
                        } else if (name.endsWith(".mp4")) {
                            videoMap.put(name, browser_download_url)
                        } else if (name == "EasterEggInfo.json") {
                            try {
                                val s = getWebInf(browser_download_url)
                                info.set(JSONArray(s))
                            } catch (e: Exception) {
                                Log.err.print(EasterEgg::class.java, "数据获取失败", e)
                            }
                        } else {
                            otherMap.put(name, browser_download_url)
                        }
                    }
                })

                //根据EasterEgg.json中的数据,修改彩蛋名
                info.get()!!.forEach(Consumer { `object`: Any? ->
                    if (`object` is JSONObject) {
                        val key = `object`.getString("key")
                        val name = `object`.getString("name")

                        keyMap.remove(key)
                        keyMap.put(name, key)

                        if (key.endsWith(".mp3")) {
                            val s = musicMap.get(key)
                            musicMap.remove(key)
                            musicMap.put(name, s)
                        } else if (key.endsWith(".mp4")) {
                            val s = videoMap.get(key)
                            videoMap.remove(key)
                            videoMap.put(name, s)
                        } else {
                            val s = otherMap.get(key)
                            otherMap.remove(key)
                            otherMap.put(name, s)
                        }
                    }
                })

                var styleInt = STYLE_EE_OTHER
                var name: String? = ""
                var url: String? = ""
                when (style) {
                    "视频" -> {
                        val names = videoMap.keys.toTypedArray<String?>()
                        val s =
                            Log.info.showChooseDialog(null, "祈愿", "请选择彩蛋", *names)
                        name = keyMap.get(s)
                        url = videoMap.get(s)
                        styleInt = STYLE_EE_VIDEO
                    }

                    "音乐" -> {
                        val names = musicMap.keys.toTypedArray<String?>()
                        val s =
                            Log.info.showChooseDialog(null, "祈愿", "请选择彩蛋", *names)
                        name = keyMap.get(s)
                        url = musicMap.get(s)
                        styleInt = STYLE_EE_MUSIC
                    }

                    "其他" -> {
                        val names = otherMap.keys.toTypedArray<String?>()
                        val s =
                            Log.info.showChooseDialog(null, "祈愿", "请选择彩蛋", *names)
                        name = keyMap.get(s)
                        url = otherMap.get(s)
                        styleInt = STYLE_EE_OTHER
                    }
                }
                showEasterEgg(styleInt, name, url)
            } catch (e: Exception) {
                Log.err.print(null, EasterEgg::class.java, "获取彩蛋失败", e)
            }
        }

    fun showEasterEgg(style: Int, name: String?, url: String?) {
        Log.info.print("EasterEgg-显示", "正在准备...")


        object : SwingWorker<Void?, Void?>() {
            override fun doInBackground(): Void? {
                // 异步下载（在后台线程执行）
                Log.info.print("EasterEgg-下载", "正在下载...")

                Log.info.print("EasterEgg-下载", "下载链接: $url")

                if (style == STYLE_EE_VIDEO) ResourceLocalizer.copyWebFile(
                    CTInfo.TEMP_PATH + "EasterEgg\\video\\",
                    url,
                    name
                )
                else if (style == STYLE_EE_MUSIC) ResourceLocalizer.copyWebFile(
                    CTInfo.TEMP_PATH + "EasterEgg\\music\\",
                    url,
                    name
                )
                else if (style == STYLE_EE_OTHER) ResourceLocalizer.copyWebFile(
                    CTInfo.TEMP_PATH + "EasterEgg\\other\\",
                    url,
                    name
                )
                return null
            }

            override fun done() {
                // 下载完成后在EDT线程执行
                try {
                    get() // 获取执行结果（可捕获异常）


                    if (style == STYLE_EE_MUSIC) {
                        val path = CTInfo.TEMP_PATH + "EasterEgg\\music\\" + name
                        MediaPlayer.playLocalMusic(path)
                    } else if (style == STYLE_EE_VIDEO) {
                        val path = CTInfo.TEMP_PATH + "EasterEgg\\video\\" + name
                        MediaPlayer.playVideo(path)
                    } else if (style == STYLE_EE_OTHER) {
                        val path = CTInfo.TEMP_PATH + "EasterEgg\\other\\" + name
                        MediaPlayer.playOther(path)
                    }
                } catch (e: Exception) {
                    Log.err.print(null, EasterEgg::class.java, "下载失败", e)
                }
            }
        }.execute()
    }


    @JvmStatic
    fun getText(style: EETextStyle): String {
        val easterEggList: Array<String>? = CTInfo.easterEggModeMap.getStringList(
            "加载文字集",
            allText
        )
        val s = easterEggList!![Random().nextInt(easterEggList.size)]
        when (style) {
            EETextStyle.DEFAULT -> {
                return s
            }

            EETextStyle.HTML -> {
                val s1 = "<html>" + s.replace("\\n", "<br>") + "</html>"
                Log.info.print("获取彩蛋文字", s1)
                return s1
            }
        }
    }

    @JvmStatic
    val allText: Array<String>
        get() {
            val list: MutableList<String> = ArrayList<String>()
            val info =
                IOForInfo.getInfo(EasterEgg::class.java.getResource("EasterEgg.txt"))
            for (s in info) {
                list.add(s)
            }
            return list.toTypedArray<String>()
        }

    @JvmStatic
    fun errorAction() {
        Log.info.print("EasterEgg", "你没有权限!!!")

        CTOptionPane.showMessageDialog(
            null,
            "doge",
            "你没有权限!!!",
            GetIcon.getIcon("彩蛋.刻律德菈", IconControl.COLOR_DEFAULT, 100, 100),
            CTOptionPane.ERROR_MESSAGE,
            true
        )
    }

    @JvmStatic
    fun showHolidayBlessings(style: Int) {
        if (isHasTheArg("EasterEgg:notShow")) return

        Log.info.print("EasterEgg", "搜索今日是否需要祝福")

        val b = AtomicBoolean(false)
        try {
            //获取文件
            val jsonArrayStr =
                IOForInfo.getInfos(EasterEgg::class.java.getResource("HBText.json"))
            val jsonArray = JSONArray(jsonArrayStr)

            //获取时间
            jsonArray.forEach(Consumer { jsonObject: Any? ->
                if (jsonObject is JSONObject) {
                    val date1 = jsonObject.getString("date")
                    if (DateTools.dayIsNow(date1)) {
                        b.set(true)
                        Main.argsList.add("-StartUpdate:false")

                        val text = jsonObject.getString("text")
                        val title = jsonObject.getString("title")

                        CTOptionPane.showFullScreenMessageDialog(title, text, 5, 5)
                    }
                } else {
                    Log.err.print(EasterEgg::class.java, "获取彩蛋文件数据异常: \n$jsonObject")
                }
            })
        } catch (e: Exception) {
            Log.err.print(EasterEgg::class.java, "获取彩蛋文字失败", e)
        }
        if (style == 1 && !b.get()) Log.info.message(null, "EasterEgg", "今日无彩蛋")
    }
}
