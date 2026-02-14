package com.wmp.classTools.importPanel.finalPanel

import com.wmp.Main.isHasTheArg
import com.wmp.classTools.CTComponent.CTButton.CTIconButton
import com.wmp.classTools.CTComponent.CTPanel.CTViewPanel
import com.wmp.classTools.CTComponent.Menu.CTPopupMenu
import com.wmp.classTools.frame.AboutDialog
import com.wmp.classTools.frame.CTTools
import com.wmp.classTools.frame.MainWindow
import com.wmp.classTools.frame.ShowCookieDialog
import com.wmp.classTools.infSet.InfSetDialog
import com.wmp.publicTools.CTInfo
import com.wmp.publicTools.EasterEgg.EasterEgg.showHolidayBlessings
import com.wmp.publicTools.UITools.CTColor
import com.wmp.publicTools.appFileControl.CTInfoControl
import com.wmp.publicTools.appFileControl.IconControl
import com.wmp.publicTools.printLog.Log
import com.wmp.publicTools.update.GetNewerVersion.checkForUpdate
import java.awt.Dimension
import java.io.IOException
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Consumer
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JOptionPane

class FinalPanel : CTViewPanel<Any?>() {
    init {
        this.setName("功能性按钮组")
        this.id = "FinalPanel"

        initPanel()

        initButton()
    }

    override fun setInfoControl(): CTInfoControl<Any?>? {
        return null
    }

    private fun initPanel() {
        this.setLayout(BoxLayout(this, BoxLayout.X_AXIS))
        this.setBackground(CTColor.backColor)
        // 添加弹性空间
        this.add(Box.createHorizontalGlue()) // 左侧弹簧
        this.add(Box.createRigidArea(Dimension(0, 0))) // 按钮间距
        this.add(Box.createHorizontalGlue()) // 右侧弹簧
    }

    private fun initButton() {
        val moreMenu = CTPopupMenu()
        moreMenu.setBackground(CTColor.backColor)


        val moreButton = CTIconButton(
            "更多功能",
            "通用.更多", IconControl.COLOR_COLORFUL
        ) {}
        moreButton.setCallback { moreMenu.show(moreButton, 0, moreButton.getHeight()) }
        moreButton.preferredSize = moreButton.size
        moreButton.maximumSize = moreButton.size
        moreButton.minimumSize = moreButton.size
        allButList.clear()

        val settings = CTIconButton(
            "设置",
            "通用.设置", IconControl.COLOR_COLORFUL
        ) {
            try {
                InfSetDialog()
            } catch (e: Exception) {
                Log.err.print(javaClass, "设置打开失败", e)
            }
        }
        allButList.add(settings)

        val cookie = CTIconButton(
            "快速启动页",
            "通用.快速启动", IconControl.COLOR_COLORFUL
        ) {
            try {
                ShowCookieDialog()
            } catch (e: IOException) {
                Log.err.print(javaClass, "打开失败", e)
            }
        }
        allButList.add(cookie)


        val about = CTIconButton(
            "软件信息",
            "通用.关于", IconControl.COLOR_COLORFUL
        ) {
            try {
                AboutDialog().isVisible = true
            } catch (e: Exception) {
                Log.err.print(javaClass, "打开失败", e)
            }
        }
        allButList.add(about)

        val CTTools = CTIconButton(
            "快捷工具",
            "通用.快捷工具", IconControl.COLOR_COLORFUL
        ) { CTTools.showDialog(2) }
        allButList.add(CTTools)

        val update = CTIconButton(
            "检查更新",
            "通用.网络.更新", IconControl.COLOR_COLORFUL
        ) { checkForUpdate(null, null, true) }
        allButList.add(update)

        // 自定义刷新方法
        val refresh = CTIconButton(
            "刷新",
            "通用.刷新", IconControl.COLOR_COLORFUL
        ) { MainWindow.refresh() }
        allButList.add(refresh)

        val holidayBlessings = CTIconButton(
            "查看祝词",
            "通用.祈愿", IconControl.COLOR_COLORFUL
        ) { showHolidayBlessings(1) }
        allButList.add(holidayBlessings)

        val showLog = CTIconButton(
            "查看日志",
            "通用.日志", IconControl.COLOR_COLORFUL
        ) { Log.showLogDialog() }
        allButList.add(showLog)


        this.add(Box.createHorizontalStrut(2))
        this.add(moreButton)
        this.add(Box.createHorizontalStrut(2)) // 按钮后添加间距

        val length = AtomicInteger()

        //按钮展示
        allButList.forEach(Consumer { ctButton: CTIconButton? ->
            if (CTInfo.disButList.contains(ctButton!!.getName())) {
                moreMenu.add(ctButton.toRoundTextButton())
                length.getAndIncrement()
            } else {
                val temp = ctButton.copy()
                temp.preferredSize = ctButton.size
                temp.maximumSize = ctButton.size
                temp.minimumSize = ctButton.size
                this.add(Box.createHorizontalStrut(2))
                this.add(temp)
                this.add(Box.createHorizontalStrut(2)) // 按钮后添加间距
            }
        })

        if (length.get() == 0) {
            this.remove(moreButton)
        }

        //设置关闭按钮
        if (CTInfo.easterEggModeMap.getBoolean("是否可以退出", true) && CTInfo.canExit && !isHasTheArg("屏保:展示")) {
            val exit = CTIconButton(
                "关闭",
                "通用.关闭", IconControl.COLOR_COLORFUL
            ) {
                val i = Log.info.showChooseDialog(null, "CTViewPanel-按钮组", "确认退出?")
                if (i == JOptionPane.YES_OPTION) {
                    Log.exit(0)
                }
            }
            exit.preferredSize = exit.size
            exit.maximumSize = exit.size
            exit.minimumSize = exit.size


            this.add(Box.createHorizontalStrut(5))
            this.add(exit)
            this.add(Box.createHorizontalStrut(5)) // 按钮后添加间距
        }
    }

    @Throws(IOException::class)
    override fun easyRefresh() {
        this.removeAll()

        initPanel()
        initButton()

        revalidate()
        repaint()
    }

    companion object {
        @JvmField
        val allButList = ArrayList<CTIconButton?>()
    }
}
