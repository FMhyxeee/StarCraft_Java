package gui.ui;

import javax.swing.border.LineBorder;
import java.awt.*;

public class RoundBorder extends LineBorder {

    public RoundBorder(Color color){
        super(color);
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Color oldColor = g.getColor();
        int i;

        g.setColor(lineColor);
        for ( i = 0; i < thickness; i++){
            g.drawRoundRect(x+i, y+i, width-i-i-1, height-i-i-1,7,7);
        }
        g.setColor(oldColor);
    }
}
