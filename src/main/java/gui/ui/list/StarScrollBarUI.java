package gui.ui.list;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class StarScrollBarUI extends BasicScrollBarUI {
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (thumbBounds.isEmpty() || !scrollbar.isEnabled()){
            return;
        }

        int w = thumbBounds.width;
        int h = thumbBounds.height;

        g.translate(thumbBounds.x, thumbBounds.y);

        g.setColor(thumbDarkShadowColor);
        g.drawRect(0,0,w - 1, h - 1);
        g.setColor(thumbColor);
        g.fillRect(0, 0, w - 1, h - 1);

        g.setColor(thumbHighlightColor);
        g.drawLine(1,1,1,h - 2);
        g.drawLine(2,1,w-3,1);

        g.setColor(thumbLightShadowColor);
        g.drawLine(2, h - 2, w - 2, h - 2);
        g.drawLine(w - 2, 1, w - 2, h - 3);

        g.translate(-thumbBounds.x, -thumbBounds.y);

    }

    @Override
    protected void configureScrollBarColors() {
        LookAndFeel.installColors(scrollbar, "ScrollBar.background",
                "ScrollBar.foreground");
        scrollbar.setBackground(Color.black);
        scrollbar.setForeground(Color.red);
        thumbHighlightColor = Color.black;//UIManager.getColor("ScrollBar.thumbHighlight");
        thumbLightShadowColor =Color.black; //UIManager.getColor("ScrollBar.thumbShadow");
        thumbDarkShadowColor = Color.black;//UIManager.getColor("ScrollBar.thumbDarkShadow");
        thumbColor = Color.red;
        trackColor = Color.black;//UIManager.getColor("ScrollBar.track");
        trackHighlightColor =Color.black;// UIManager.getColor("ScrollBar.trackHighlight");

    }

    public static ComponentUI createUI(JComponent c) {
        return new StarScrollBarUI();
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return new MyArrowButton(orientation);
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return new MyArrowButton(orientation);
    }

    private class MyArrowButton extends BasicArrowButton {

        public MyArrowButton(int direction) {
            super(direction);

        }

        @Override
        public void paint(Graphics g) {
            g.setColor(Color.red);
            Polygon filledPolygon = new Polygon();
            if (direction == NORTH) {

                filledPolygon.addPoint(8, 0);
                filledPolygon.addPoint(0, 14);
                filledPolygon.addPoint(15, 14);

            } else {

                filledPolygon.addPoint(0, 0);
                filledPolygon.addPoint(15, 0);
                filledPolygon.addPoint(8, 14);
            }
            g.drawPolygon(filledPolygon);

        }
    }
}
