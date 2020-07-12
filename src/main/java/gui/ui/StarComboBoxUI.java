package gui.ui;

import core.ResourceManager;
import gui.ui.combobox.StarComboPopup;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;

public class StarComboBoxUI extends BasicComboBoxUI {
    private static Image arrow = ResourceManager.loadImage("arrow.png");

    Color bg = new Color(0,0,50);

    Color heightTx = new Color(175,250,110);

    @Override
    protected JButton createArrowButton() {
        JButton button = new MyArrowButton();
        comboBox.setBackground(bg);
        comboBox.setForeground(heightTx);

        button.setIgnoreRepaint(true);
        button.setFocusable(false);
        button.setContentAreaFilled(false);
        return button;
    }

    @Override
    protected ComboPopup createPopup() {
        return new StarComboPopup(comboBox);
    }

    public static ComponentUI createUI(JComponent c) {
        return new StarComboBoxUI();
    }

    private class MyArrowButton extends JButton {

        @Override
        public void paint(Graphics g) {
            g.setColor(bg);
            g.fillRoundRect(-1, 0, getWidth(), getHeight(), 6, 6);
            g.drawImage(arrow, -2, 0, null);
        }
    }
}
