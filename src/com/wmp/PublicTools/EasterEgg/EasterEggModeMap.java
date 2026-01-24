package com.wmp.PublicTools.EasterEgg;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class EasterEggModeMap {
    private final Map<String, Object> map = new HashMap<>();

    /**
     * 彩蛋模式参数(不修改的数据写进排除列表)
     *
     * @param version               版本
     * @param author                作者
     * @param appName               软件名称
     * @param iconPath              图标路径
     * @param optionPaneTitle       提示窗标题
     * @param optionPaneIsUseIcon   提示窗是否使用图标
     * @param backColor             背景色
     * @param textColor             文字色
     * @param mainColor             主题色
     * @param themeMode             主题模式
     * @param canExit               是否可以退出
     * @param whenEasterEggStart    彩蛋启动运行
     * @param excludes              排除的参数
     * @author wmp
     * @since 2.0.6.1.2
     */
    public EasterEggModeMap(String version, String author, String appName,
                            String iconPath, String optionPaneTitle, boolean optionPaneIsUseIcon,
                            Color backColor, Color textColor, Color mainColor, String themeMode,
                            boolean canExit, Runnable whenEasterEggStart, String... excludes) {
        map.put("版本", version);
        map.put("作者", author);
        map.put("软件名称", appName);
        map.put("图标路径", iconPath);
        map.put("提示窗标题", optionPaneTitle);
        map.put("提示窗是否使用图标", optionPaneIsUseIcon);
        map.put("背景色", backColor);
        map.put("文字色", textColor);
        map.put("主题色", mainColor);
        map.put("主题模式", themeMode);
        map.put("是否可以退出", canExit);
        map.put("彩蛋启动运行", whenEasterEggStart);

        for (String exclude : excludes) {
            map.remove(exclude);
        }

    }

    /**
     * 添加更多彩蛋数据
     * @see #get(String, Object)
     * @param pair 彩蛋的键值对
     */
    public void addMore(EasterEggPair... pair){
        for (EasterEggPair easterEggPair : pair) {
            map.put(easterEggPair.key(), easterEggPair.value());
        }

    }
    /**
     * 获取参数
     * @param key 可用参数
     *            <ul>
     *                <code>版本</code>
     *                <code>作者</code>
     *                <code>软件名称</code>
     *                <code>图标路径</code>
     *                <code>提示窗标题</code>
     *                <code>提示窗是否使用图标</code>
     *                <code>背景色</code>
     *                <code>文字色</code>
     *                <code>主题色</code>
     *                <code>主题模式</code>
     *                <code>是否可以退出</code>
     *                <code>彩蛋启动运行</code>
     *            <code>加载文字集</code>
     *            <code>关闭文字集</code>
     *            </ul>
     * @param defaultValue 默认值
     * @return 参数
     */
    public Object get(String key, Object defaultValue) {
        return map.getOrDefault(key, defaultValue);
    }

    /**
     * @see #get(String, Object)
     */
    public String getString(String key, String defaultValue) {
        return (String) map.getOrDefault(key, defaultValue);
    }

    /**
     * @see #get(String, Object) 
     */
    public String[] getStringList(String key, String[] defaultValue){
        return (String[]) map.getOrDefault(key, defaultValue);
    }

    /**
     * @see #get(String, Object)
     */
    public int getInt(String key, int defaultValue) {
        return (int) map.getOrDefault(key, defaultValue);
    }

    /**
     * @see #get(String, Object)
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        return (boolean) map.getOrDefault(key, defaultValue);
    }

    /**
     * @see #get(String, Object)
     */
    public Color getColor(String key, Color defaultValue) {
        return (Color) map.getOrDefault(key, defaultValue);
    }

    /**
     * @see #get(String, Object)
     */
    public Runnable getRunnable(String key, Runnable defaultValue) {
        Object o = map.get(key);
        if (o == null) {
            System.err.printf("参数%s不存在", key);
            return defaultValue;
        }else return (Runnable) map.getOrDefault(key, defaultValue);
    }
}
