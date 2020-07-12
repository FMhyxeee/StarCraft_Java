package gui;

import core.GridMapRender;
import core.ResourceManager;

import javax.swing.*;
import java.awt.*;

public class ConsolePanel extends JPanel {
    Image main = ResourceManager.loadImage("console.gif");

    GridMapRender tileMap;

    public MapPanel map_panel;
    public WorkPanel work_panel;

    public ConsolePanel(GridMapRender gridMapRender) {
        super();
        setFocusable(false);
        setLayout(null);
        setSize(main.getWidth(null), main.getHeight(null));

        this.tileMap = gridMapRender;
        map_panel = new MapPanel(gridMapRender);
        work_panel = new WorkPanel(gridMapRender);
        map_panel.setLocation(10, 20);
        map_panel.setLocation(10, 158);
        add(map_panel);
        add(work_panel);
        gridMapRender.setConsolePanel(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(main, 0, 0, null);
    }
}
