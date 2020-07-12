package core;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public abstract class GameCore extends JFrame {

    protected static final int FONT_SIZE = 10;

    private boolean isRunning;

    protected JFrame window;

    public void stop(){

    }

    public void run(){
        init();
        gameLoop();
    }

    public void init() {

        setUndecorated(true);
        setTitle("JStarCraft");
        setIconImage(ResourceManager.loadImage("title.png"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setVisible(true);
        setIgnoreRepaint(true);
        setResizable(false);
        setFont(new Font("Dialog", Font.PLAIN, FONT_SIZE));
        setBackground(Color.black);
        setForeground(Color.white);
        createBufferStrategy(2);
        isRunning = true;
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                ResourceManager.loadImage("cur.png"), new Point(0, 0), "cur"));
        window = getWindow();
        NullRepaintManager.install();
        window.setLayout(null);
        Container contentPane = getWindow().getContentPane();
        ((JComponent) contentPane).setOpaque(false);

    }

    public void gameLoop() {

        BufferStrategy strategy = getBufferStrategy();
        long startTime = System.currentTimeMillis();
        long currTime = startTime;
        while (isRunning) {

            long elapsedTime = System.currentTimeMillis() - currTime;
            currTime += elapsedTime;

            // update
            update(elapsedTime);

            // draw the screen
            Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            // g.drawImage(ResourceManager.loadImage("background3.jpg"), 0, 33,
            // null);
            draw(g);
            g.dispose();

            if (!strategy.contentsLost()) {
                strategy.show();
            }

            // take a nap
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
            }
        }
    }

    public void update(long elapsedTime) {
        // do nothing
    }

    public abstract void draw(Graphics2D g);

    public JFrame getWindow() {
        return this;
    }
}
