package gui;

import core.ResourceManager;

import java.awt.*;

public class ProgressPanel extends  Abstractpanel{

    Image main = ResourceManager.loadImage("background.jpg");

    public ProgressPanel(GameGUI gameGUI, String name) {
        super(gameGUI, name);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(main, 0, 0, null);
        g.drawRect(100, getHeight() - 50, 600, 15);
        g.fillRect(100, getHeight()-50, ResourceManager.Constant.getProgress(),15);

        if (ResourceManager.Constant.getProgress() >= 590){
            GameGUI parent = (GameGUI) this.getParent();
            parent.switchGame();
        }
    }
}
