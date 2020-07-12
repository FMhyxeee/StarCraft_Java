package gui.ui.combobox;

import core.ResourceManager;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;

public class StarComboBoxUI extends BasicComboBoxUI {
    private static Image arrow = ResourceManager.loadImage("arrow.png");

    private static Color bg = new Color(0, 0, 50);

    private static Color text = new Color(76,196,40);

    static{
        UIManager.put("ComboBox.disabledForeground", Color.gray);
        UIManager.put("ComboBox.disabledBackground", bg);
    }

    @Override
    protected JButton createArrowButton() {

        JButton button = new MyArrowButton();
        comboBox.setBackground(bg);
        comboBox.setForeground(text);

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

    @SuppressWarnings("serial")
    private class MyArrowButton extends JButton {

        @Override
        public void paint(Graphics g) {
            g.setColor(bg);
            g.fillRoundRect(-1, 0, getWidth(), getHeight(), 6, 6);
            g.drawImage(arrow, -2, 0, null);

        }
    }
}
