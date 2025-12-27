package com.wmp.classTools.CTComponent.CTProgressBar;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;

public class CircleLoader extends JPanel {
    private boolean isIndeterminate = false;

    private float rotationAngle = 0;
    private float sweepAngle = 45; // 扇形角度
    private final Timer timer;

    public CircleLoader() {
        setPreferredSize(new Dimension(120, 120));
        setOpaque(false); // 透明背景

        // 定时器控制动画
        timer = new Timer(20, _ -> {

                rotationAngle += 5;
                if (rotationAngle >= 360) {
                    rotationAngle = 0;
                }

            repaint();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // 启用抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width, height) - 20;
        int x = (width - size) / 2;
        int y = (height - size) / 2;

        // 绘制外圈
        g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
        g2d.setColor(new Color(200, 200, 200, 100));
        g2d.drawOval(x, y, size, size);

        // 创建渐变色
        GradientPaint gradient = new GradientPaint(
                x, y, new Color(0, 120, 212, 200),
                x + size, y + size, new Color(0, 153, 255, 150)
        );
        g2d.setPaint(gradient);

        // 绘制旋转的扇形
        g2d.setStroke(new BasicStroke(6, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));

        Arc2D arc = new Arc2D.Float(x, y, size, size,
                rotationAngle, sweepAngle,
                Arc2D.OPEN);
        g2d.draw(arc);

        g2d.dispose();
    }

    public void startAnimation() {
        timer.start();
    }

    public void stopAnimation() {
        timer.stop();
    }

    public void setIndeterminate(boolean indeterminate){
        isIndeterminate = indeterminate;

    }

    public boolean isIndeterminate() {
        return isIndeterminate;
    }

    public void setValue(int value) {
        if (isIndeterminate) return;
        //角度0~360
        //百分比0~100
        sweepAngle = (float) (value * 3.6);
        repaint();
    }

    public void setLoading(boolean loading) {
        if (loading) {
            timer.start();
        } else {
            timer.stop();
        }
    }
}