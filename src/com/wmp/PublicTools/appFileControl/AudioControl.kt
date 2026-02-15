package com.wmp.publicTools.appFileControl

import com.wmp.classTools.CTComponent.CTBorderFactory
import com.wmp.classTools.CTComponent.CTButton.CTTextButton
import com.wmp.classTools.CTComponent.CTOptionPane
import com.wmp.classTools.CTComponent.CTTextField
import com.wmp.publicTools.CTInfo
import com.wmp.publicTools.UITools.CTFont
import com.wmp.publicTools.UITools.CTFontSizeStyle
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
import javazoom.jl.decoder.JavaLayerException
import javazoom.jl.player.Player
import org.json.JSONArray
import org.json.JSONObject
import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.Font
import java.awt.event.ActionEvent
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.nio.file.Path
import java.util.concurrent.atomic.AtomicReference
import java.util.function.Consumer
import javax.swing.JDialog
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.event.TreeExpansionEvent
import javax.swing.event.TreeExpansionListener
import javax.swing.event.TreeSelectionEvent
import javax.swing.tree.DefaultMutableTreeNode

object AudioControl {
    private val PLAYER_MAP: MutableMap<String?, Player?> = HashMap()

    private val ALL_AUDIO_KEY = arrayOf<String?>(
        "系统.错误", "系统.警告", "系统.通知",
        "课程表.上课", "课程表.下课"
    )

    fun init(getNewerVersion: Boolean) {
        PLAYER_MAP.forEach { (_: String?, player: Player?) ->
            if (player!!.isComplete) {
                player.close()
            }
        }
        PLAYER_MAP.clear()

        try {
            //获取基础音频

            val resourceInfos = getInfos(AudioControl::class.java.getResource("musicIndex.json")!!)
            val resourceJsonArray = JSONArray(resourceInfos)
            resourceJsonArray.forEach(Consumer { `object`: Any? ->
                val jsonObject = `object` as JSONObject
                Log.info.print(
                    "AudioControl",
                    String.format("名称:%s|位置:%s", jsonObject.getString("name"), jsonObject.getString("path"))
                )
                val pathStr = jsonObject.getString("path")
                val path = AudioControl::class.java.getResource(pathStr)
                if (path == null) {
                    Log.warn.print("AudioControl", String.format("音频文件%s不存在", jsonObject.getString("path")))
                } else {
                    PLAYER_MAP[jsonObject.getString("name")] =
                        getPlayer(jsonObject.getString("name"), AudioControl::class.java.getResourceAsStream(pathStr))
                }
            })
        } catch (e: Exception) {
            Log.warn.message(null, AudioControl::class.java.getName(), "音频加载失败:\n$e")
        }
        if (getNewerVersion) {
            try {
                //判断磁盘中是否有音频
                newMusic
            } catch (e: Exception) {
                Log.warn.print(null, AudioControl::class.java.getName(), "音频更新失败:\n$e")
            }
        }

        try {
            //获取磁盘中的图标
            val musicInfos = getInfos(CTInfo.APP_INFO_PATH + "music\\musicIndex.json")

            val musicJsonArray = JSONArray(musicInfos)
            musicJsonArray.forEach(Consumer { `object`: Any? ->
                val jsonObject = `object` as JSONObject
                Log.info.print(
                    "AudioControl",
                    String.format("名称:%s|位置:%s", jsonObject.getString("name"), jsonObject.getString("path"))
                )
                val pathStr = File(CTInfo.APP_INFO_PATH, jsonObject.getString("path")).path
                try {
                    val file = File(pathStr)
                    if (!file.exists()) {
                        Log.warn.print("AudioControl", String.format("音频文件%s不存在", jsonObject.getString("path")))
                    } else {
                        PLAYER_MAP[jsonObject.getString("name")] =
                            getPlayer(jsonObject.getString("name"), FileInputStream(file))
                    }
                } catch (_: Exception) {
                    Log.warn.print("AudioControl", String.format("音频文件%s不存在", jsonObject.getString("path")))
                }
            })
        } catch (e: Exception) {
            Log.warn.message(null, AudioControl::class.java.getName(), "本地音频加载失败:\n$e")
        }
    }

    @JvmStatic
    fun showControlDialog() {
        val controlDialog = JDialog()
        controlDialog.setTitle("音频控制")
        controlDialog.setModal(true)
        controlDialog.contentPane.setLayout(BorderLayout())

        val controlPanel = JPanel()
        controlPanel.setLayout(FlowLayout(FlowLayout.LEFT, 10, 10))
        controlPanel.setOpaque(false)
        controlPanel.setBorder(CTBorderFactory.createTitledBorder("音频管理"))

        val selectAudio = CTTextField()
        selectAudio.setFont(CTFont.getCTFont(Font.BOLD, CTFontSizeStyle.BIG))
        selectAudio.isEditable = false
        controlPanel.add(selectAudio)

        val player = arrayOf<Player?>(null)
        val playerButton = CTTextButton("播放")
        playerButton.addActionListener{ _: ActionEvent? ->
            if (player[0] != null) {
                try {
                    player[0]!!.play()
                } catch (ex: JavaLayerException) {
                    Log.err.print(AudioControl::class.java, "音频播放失败", ex)
                }
                return@addActionListener
            }
            Log.err.print(AudioControl::class.java.toString(), "音频异常失败")
        }
        controlPanel.add(playerButton)

        val showTree = GetShowTreePanel.getShowTreePanel(ALL_AUDIO_KEY, "音频")
        showTree.addTreeExpansionListener(object : TreeExpansionListener {
            override fun treeExpanded(event: TreeExpansionEvent?) {
                controlDialog.pack()
            }

            override fun treeCollapsed(event: TreeExpansionEvent?) {
                controlDialog.pack()
            }
        })
        showTree.addTreeSelectionListener{ e: TreeSelectionEvent? ->
            if (showTree.getLastSelectedPathComponent() != null &&
                showTree.getLastSelectedPathComponent() is DefaultMutableTreeNode
            ) {
                //获取Key
                val path = e!!.path.getPath()
                val key = StringBuilder()
                for (i in 1..<path.size) {
                    key.append(path[i])
                    if (i != path.size - 1) {
                        key.append(".")
                    }
                }

                player[0] = getPlayer(key.toString())
                selectAudio.text = key.toString()

                controlDialog.pack()
                controlDialog.repaint()
            }
        }

        val showPanel = JPanel()
        showPanel.setLayout(BorderLayout())
        showPanel.setBorder(CTBorderFactory.createTitledBorder("音频列表"))
        showPanel.add(showTree)

        controlDialog.contentPane.add(JScrollPane(showPanel), BorderLayout.CENTER)
        controlDialog.contentPane.add(JScrollPane(controlPanel), BorderLayout.SOUTH)

        controlDialog.pack()
        controlDialog.setLocationRelativeTo(null)
        controlDialog.isVisible = true
    }

    fun getPlayer(key: String?): Player? {
        return PLAYER_MAP[key]
    }

    @get:Throws(InterruptedException::class)
    private val newMusic: Unit
        get() {
            var needDownload = false
            val jsonObject = JSONObject(
                getWebInf("https://api.github.com/repos/wmp666/ClassTools_Music/releases/latest", false)
            )
            val downloadURL = AtomicReference("")
            val version = AtomicReference("")
            val oldVersion = getInfo(CTInfo.APP_INFO_PATH + "music\\version.txt")!![0]
            //判断是否存在
            version.set(jsonObject.getString("tag_name"))
            jsonObject.getJSONArray("assets").forEach(Consumer { `object`: Any? ->
                val asset = `object` as JSONObject
                if (asset.getString("name") == "music.zip") {
                    downloadURL.set(asset.getString("browser_download_url"))
                }
            })

            if (oldVersion != "err") {
                val split: Array<String?> =
                    oldVersion.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val newerVersion = GetNewerVersion.isNewerVersion(version.get()!!, split[split.size - 1]!!)
                if (newerVersion != 0) {
                    if (split[0] != "zip") {
                        Log.info.print("AudioControl", "有新音频")
                        needDownload = true
                    } else {
                        val i = Log.warn.showChooseDialog(
                            null,
                            "AudioControl",
                            "我们已经更新了官方音频库,而您的音频似乎是使用压缩包导入的(可能为第三方),我们无法确认是否要更新,如果你已经有了相关的最新版本/想要使用官方音频库,请按\"是\",否则按\"否\""
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
                    Log.err.print("AudioControl", "音频更新失败")
                }
            }
        }

    @JvmStatic
    @Throws(InterruptedException::class)
    fun downloadFile(downloadURL: AtomicReference<String?>?, version: AtomicReference<String?>): Boolean {
        val choose: String
        if (downloadURL != null && !downloadURL.get()!!.isEmpty()) {
            choose = Log.info.showChooseDialog(
                null,
                "AudioControl",
                "音频文件不存在/存在新版,选择获取方式",
                "下载",
                "导入压缩包"
            )
        } else choose = "导入压缩包"

        val zipPath: String?


        when (choose) {
            "下载" -> {
                //下载文件
                val b = DownloadURLFile.downloadWebFile(null, null, downloadURL!!.get(), CTInfo.TEMP_PATH + "appInfo")
                if (!b) return false
                zipPath = CTInfo.TEMP_PATH + "appInfo\\music.zip"
            }
            "导入压缩包" -> {
                Log.warn.message(null, "AudioControl", "若导入的音频库为第三方,可能需要自行更新")
                version.set("zip:" + version.get())
                zipPath = GetPath.getFilePath(null, "导入音频", ".zip", "音频压缩包")
            }
            else -> {
                Log.warn.message(null, "AudioControl", "若不下载/导入音频,可能造成程序异常")
                return false
            }
        }
        //清空文件
        deleteDirectoryRecursively(Path.of(CTInfo.APP_INFO_PATH + "music"))

        //解压文件
        val thread = ZipPack.unzip(zipPath, CTInfo.APP_INFO_PATH)

        if (thread != null) {
            thread.join()
        }
        //生成版本文件
        try {
            IOForInfo(CTInfo.APP_INFO_PATH + "music\\version.txt").setInfo(version.get()!!)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        return true
    }

    private fun getPlayer(key: String?, inputStream: InputStream?): Player? {
        if (!listOf(*ALL_AUDIO_KEY).contains(key)) return null
        if (inputStream != null) {
            try {
                return Player(inputStream)
            } catch (`_`: JavaLayerException) {
            }
        }
        return null
    }
}
