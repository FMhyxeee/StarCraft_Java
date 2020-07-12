package gui;

import core.GridMapRender;
import core.ResourceManager;
import net.NetWorkManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class GameGUI extends JPanel {

    private CardLayout cardLayout = new CardLayout();

    private ProgressPanel progressPanel;

    private MainMenuPanel menuPanel;

    private GamePanel gamePanel;

    private Abstractpanel currentPanel;

    private SingleGamePanel singleGamePanel;

    private MutilPlayerPanel multiPlayerPanel;

    NewGamePanel newGamePanel;

    NetWorkManager netWorkManager;

    GridMapRender mapRender;

    JFrame frame;

    public GameGUI(JFrame frame){
        this.frame = frame;
        setLayout(cardLayout);
        getBorder();
        frame.getContentPane().add(this);
        setSize(frame.getWidth(), frame.getHeight());

        currentPanel = menuPanel = new MainMenuPanel(this, "mainMenu");
        progressPanel = new ProgressPanel(this, "progress");
        gamePanel = new GamePanel(this, "game");
        singleGamePanel = new SingleGamePanel(this, "singleGame");
        multiPlayerPanel = new MutilPlayerPanel(this, "mutilGame");
        newGamePanel = new NewGamePanel(this, "newGame");
    }

    public void switchProgress(final int type, final List<Integer> types){
        cardLayout.show(this, "progress");
        currentPanel = progressPanel;

        Thread t = new Thread(()->{
            mapRender = ResourceManager.load(type, types);
            mapRender.setNetWorkManager(netWorkManager);
        });
        t.start();
    }

    public void switchGame(){
        gamePanel.init();
        cardLayout.show(this, "game");
        currentPanel = gamePanel;
    }

    public void switchSingleGame(){
        cardLayout.show(this, "singleGame");
        singleGamePanel.reset();
        currentPanel = singleGamePanel;
    }

    public void switchMutilGame(){

        cardLayout.show(this, "mutilGame");
        multiPlayerPanel.reset();
        currentPanel = multiPlayerPanel;
    }

    public void switchMainMenu(){
        cardLayout.show(this, "mainMenu");
        menuPanel.reset();
        currentPanel = menuPanel;

    }

    public void switchNewGame(){
        cardLayout.show(this, "newGame");
        newGamePanel.reset();
        currentPanel = newGamePanel;
    }

    public void update(long elapsedTime){
        if (currentPanel != null){
            currentPanel.update(elapsedTime);
        }
    }
}
