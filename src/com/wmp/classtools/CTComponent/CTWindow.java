package com.wmp.classTools.CTComponent;

import com.wmp.PublicTools.CTInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.awt.geom.RoundRectangle2D;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

public class CTWindow extends JFrame implements WindowStateListener {
    public CTWindow() throws HeadlessException {
        this.setUndecorated(true);
        this.setShape(new RoundRectangle2D.Double(0, 0, this.getWidth(), this.getHeight(), CTInfo.arcw - 10, CTInfo.arch -10));
        this.setBackground(new Color(0, 0, 0, 0));

        Container contentPane = new JPanel(){
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), CTInfo.arcw, CTInfo.arch);
                g2.dispose();
            }
        };
        this.setContentPane(contentPane);

        this.addWindowStateListener(this);
    }

    @Override
    public void setSize(Dimension d) {
        super.setSize(d);

        this.setShape(new RoundRectangle2D.Double(0, 0, this.getWidth(), this.getHeight(), CTInfo.arcw, CTInfo.arch));

    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        this.setShape(new RoundRectangle2D.Double(0, 0, this.getWidth(), this.getHeight(), CTInfo.arcw, CTInfo.arch));
    }
    @Override
    public void windowStateChanged(WindowEvent e) {
        if (e.getNewState() == JFrame.ICONIFIED) {
            this.setState(JFrame.NORMAL);
        }
    }
}
