package com.wmp.classTools

import com.wmp.Main.isHasTheArg
import com.wmp.classTools.frame.LoadingWindow
import com.wmp.classTools.frame.MainWindow
import com.wmp.publicTools.CTInfo
import com.wmp.publicTools.EasterEgg.EasterEgg
import com.wmp.publicTools.UITools.CTFont
import com.wmp.publicTools.UITools.CTFontSizeStyle
import com.wmp.publicTools.printLog.Log
import com.wmp.publicTools.update.GetNewerVersion
import java.awt.Font
import java.awt.Insets
import java.io.IOException
import java.util.concurrent.atomic.AtomicReference
import javax.swing.UIManager
import javax.swing.plaf.FontUIResource

object SwingRun {
    @Throws(IOException::class)
    fun show() {
        Log.info.loading.showDialog("窗口加载", "正在加载...")

        //更新UI
        try {
            //设置默认字体
            val fontRes = FontUIResource(CTFont.getDefaultFont(Font.PLAIN, CTFontSizeStyle.SMALL))
            val keys = UIManager.getDefaults().keys()
            while (keys.hasMoreElements()) {
                val key = keys.nextElement()
                val value = UIManager.get(key)
                if (value is FontUIResource) UIManager.put(key, fontRes)
            }
            UIManager.put("PopupMenu.borderInsets", Insets(5, 10, 5, 10))
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

        //播放彩蛋启动代码
        CTInfo.easterEggModeMap.getRunnable("彩蛋启动运行", {}).run()

        Log.info.loading.updateDialog("窗口加载", "正在显示加载界面...")
        val loadingWindowRef = AtomicReference<LoadingWindow?>()

        loadingWindowRef.set(LoadingWindow())

        Log.info.loading.closeDialog("窗口加载")


        MainWindow(CTInfo.DATA_PATH)
        loadingWindowRef.get()!!.isVisible = false

        if (!(isHasTheArg("屏保:展示") ||
                    isHasTheArg("screenProduct:view"))) {
            EasterEgg.showHolidayBlessings(0)
        }

        if (CTInfo.StartUpdate &&
            !(isHasTheArg("StartUpdate:false") ||
                    isHasTheArg("屏保:展示") ||
                    isHasTheArg("screenProduct:view"))
        ) {
            Log.info.print("Main", "开始启动自动检查更新")
            GetNewerVersion.checkForUpdate(
                loadingWindowRef.get(), null, true, false
            )
        }
    }
}
