package gui;

import core.GridMap;
import core.GridMapRender;
import core.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class ManPanel extends JPanel {

    private static final Color bg = new Color(23,27,36);

    private static final Map<Integer, Color> TYPE_COLOR = new HashMap<>();

    static {
        TYPE_COLOR.put(0, Color.red);
        TYPE_COLOR.put(1, Color.yellow);
        TYPE_COLOR.put(2, Color.blue);
        TYPE_COLOR.put(3, new Color(33,146,115));
    }

    MouseListener listener = new MouseListener();

    int x, y;
    int width, height;
    BufferedImage buffer = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
    GridMap gridMap;


    public ManPanel(GridMapRender gridMapRender){
        this.gridMap = gridMapRender.getGridMap();
        setSize(128, 128);
        setFocusable(false);

        width = GridMapRender.pxToTileX(gridMap.getTileMapRender().screenWidth) + 1;
        height = GridMapRender.pxToTileX(gridMap.getTileMapRender().screenHeight) + 1;

        this.addMouseListener(listener);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) buffer.getGraphics();
        g2.drawImage(ResourceManager.Constant.IMAGE_MAP_BG,0, 0, null);

        g2.setColor(Color.GREEN);

        for (int y = 0; y < gridMap.getHeight(); ++y) {
            for (int x = 0; x < gridMap.getWidth(); ++x) {

                if(gridMap.contains(x, y)){
                    Color temp = g2.getColor();
                    Color color = TYPE_COLOR.get(gridMap.getTile(x, y).getType());
                    g2.setColor(color);
                    g2.fillRect(x, y, 1, 1);
                    g2.setColor(temp);
                }

            }
        }
        g2.dispose();
        g.drawImage(buffer, 0, 0, null);
        g.setColor(Color.red);
        g.drawRect(x, y, width, height);
    }

    private class MouseListener extends MouseAdapter{

        @Override
        public void mousePressed(MouseEvent e) {
            x = Math.min(e.getX(), getWidth()-width-1);
            y = Math.min(e.getX(), getHeight()-height-1);

            gridMap.getTileMapRender().offsetX = GridMapRender.tileXToPx(x);
            gridMap.getTileMapRender().offsetY = GridMapRender.tileYToPx(y);
        }
    }
}
