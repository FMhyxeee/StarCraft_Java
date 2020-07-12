package test;

import core.FullGameCore;
import core.ResourceManager;
import gui.GameGUI;

import java.awt.*;
import java.net.InetAddress;

public class TestGameCore extends FullGameCore {
    GameGUI gamePanel;


    @Override
    public void init() {
        super.init();
        gamePanel = new GameGUI(getWindow());
    }

    @Override
    public void draw(Graphics2D g) {

        getWindow().getLayeredPane().paintComponents(g);

    }

    @Override
    public void update(long elapsedTime) {

        gamePanel.update(elapsedTime);
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {

        if(args.length>0){
            ResourceManager.Constant.IP = args[0];
        }else{
            ResourceManager.Constant.IP = InetAddress.getLocalHost().getHostAddress();
        }
        new TestGameCore().run();
    }
}
