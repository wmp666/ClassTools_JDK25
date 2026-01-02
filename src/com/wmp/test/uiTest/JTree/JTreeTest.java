package com.wmp.test.uiTest.JTree;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.*;

public class JTreeTest extends JFrame {
    public static void main(String[] args) {
        FlatMacLightLaf.setup();
        new JTreeTest();
    }

    public JTreeTest() throws HeadlessException {
        String[] data = new String[]{
                "系统.开机", "系统.关机", "系统.啊.我", "系统.啊.发"
        };

        // 构建树形结构
        DefaultMutableTreeNode root = buildTreeFromData(data);

        JTree tree = new JTree(root);
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            System.out.println("是否为叶子节点:" + (selectedNode.getChildCount() == 0));
            System.out.println("选中的节点：" + selectedNode.getUserObject());
            System.out.println("选中的节点路径：" + e.getPath());
        });

        this.setTitle("测试");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.add(new JScrollPane(tree)); // 添加滚动面板
        this.setVisible(true);
    }

    /**
     * 从字符串数组构建树形结构
     */
    private DefaultMutableTreeNode buildTreeFromData(String[] data) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("根节点");
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
                    DefaultMutableTreeNode node = new DefaultMutableTreeNode(parts[i]);
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
