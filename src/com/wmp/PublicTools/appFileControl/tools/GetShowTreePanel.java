package com.wmp.publicTools.appFileControl.tools;

import com.wmp.publicTools.UITools.CTFont;
import com.wmp.publicTools.UITools.CTFontSizeStyle;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GetShowTreePanel {
    /**
     * 获取显示树形结构面板
     * @param data  数据集,每个数据中父级和子级之间用"."分隔
     * @return JTree
     */
    public static JTree getShowTreePanel(String[] data, String rootName) {
        DefaultMutableTreeNode root = buildTreeFromData(data, rootName, null);

        return new JTree(root);
    }

    /**
     * 获取显示树形结构面板
     * @param data  数据集,每个数据中父级和子级之间用"."分隔
     * @param imageIconMap 图片图标
     * @return JTree
     */
    public static JTree getShowTreePanel(String[] data, String rootName, Map<String, ImageIcon> imageIconMap) {
        DefaultMutableTreeNode root = buildTreeFromData(data, rootName, imageIconMap);

        JTree tree = new JTree(root);
        tree.setCellRenderer(new DefaultTreeCellRenderer(){
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                Object userObject = node.getUserObject();

                if (userObject instanceof IconNode(String name, ImageIcon icon)) {
                    setFont(CTFont.getDefaultFont(Font.PLAIN, CTFontSizeStyle.SMALL));
                    setText(name);
                    if (icon != null) {
                        setIcon(new ImageIcon(icon.getImage().getScaledInstance(getFont().getSize(), getFont().getSize(), Image.SCALE_SMOOTH)));
                    }
                }
                return this;
            }
        });
        return tree;
    }

    private static DefaultMutableTreeNode buildTreeFromData(String[] data, String rootName, Map<String, ImageIcon> imageIconMap) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootName);

        Map<String, DefaultMutableTreeNode> nodeMap = new HashMap<>();

        for (String path : data) {
            String[] parts = path.split("\\.");
            DefaultMutableTreeNode parentNode = root;

            // 遍历路径的每个部分，构建树形结构
            StringBuilder currentPath = new StringBuilder();
            for (int i = 0; i < parts.length; i++) {
                if (i > 0) {
                    currentPath.append(".");
                }
                currentPath.append(parts[i]);

                String fullPath = currentPath.toString();

                // 检查是否已存在该节点
                if (!nodeMap.containsKey(fullPath)) {
                    DefaultMutableTreeNode node = new DefaultMutableTreeNode(new IconNode(parts[i], null));
                    if (imageIconMap != null && imageIconMap.containsKey(fullPath)) {
                        node.setUserObject(new IconNode(parts[i], imageIconMap.get(fullPath)));
                    }
                    nodeMap.put(fullPath, node);
                    parentNode.add(node);
                }

                parentNode = nodeMap.get(fullPath);
            }
        }
        System.out.println(nodeMap);
        return root;
    }
}

record IconNode(String name, ImageIcon icon){
    @Override
    public @NotNull String toString() {
        return name;
    }
}
