package com.wmp.publicTools.videoView

import com.wmp.publicTools.appFileControl.AudioControl
import com.wmp.publicTools.printLog.Log
import java.awt.Desktop
import java.io.File
import java.io.IOException
import java.util.*

object MediaPlayer {
    @JvmStatic
    fun playVideo(filePath: String) {
        val videoFile = File(filePath)

        try {
            Desktop.getDesktop().open(videoFile)
        } catch (e: IOException) {
            // 处理打开视频文件失败的情况
            Log.err.print(MediaPlayer::class.java, "播放出现错误", e)
        }
    }

    fun playLocalMusic(filePath: String) {
        val musicFile = File(filePath)

        try {
            Desktop.getDesktop().open(musicFile)
        } catch (e: IOException) {
            // 处理打开视频文件失败的情况
            Log.err.print(MediaPlayer::class.java, "播放出现错误", e)
        }
    }

    @JvmStatic
    fun playOther(filePath: String) {
        val file = File(filePath)

        try {
            Desktop.getDesktop().open(file)
        } catch (e: IOException) {
            // 处理打开视频文件失败的情况
            Log.err.print(MediaPlayer::class.java, "播放出现错误", e)
        }
    }

    @JvmStatic
    fun playMusic(vararg keys: String?) {
        //key = keys[0].key[1].key[2] ... key[n]
        val sb = StringBuilder()
        for (i in keys.indices) {
            sb.append(keys[i])
            if (i != keys.size - 1) {
                sb.append(".")
            }
        }
        val key = sb.toString()

        Log.info.print(MediaPlayer::class.java.getName(), "播放音频:" + key)
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                val player = AudioControl.getPlayer(key)
                if (player != null) {
                    try {
                        player.play()
                    } catch (`_`: Exception) {
                    }
                }
                timer.cancel()
            }
        }, 0)
    }
}