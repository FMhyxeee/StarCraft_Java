package core;

import gui.ConsolePanel;
import icon.BaseIcon;
import net.NetWorkManager;
import net.datagram.SpriteInfo;
import particles.PSExplosion;
import tile.Headquarter;
import tile.Sprite;
import tile.Tile;
import util.Resource;

import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GridMapRender {
    public static int TILE_WIDTH = 42;

    public static int TILE_HEIGHT = 36;

    public static int BG_HEIGHT = 32;

    public static int BG_WIDTH = 64;

    public int screenWidth, screenHeight;

    public int offsetX, offsetY;

    public int moveX, moveY;

    int type = 0;

    GridMap gridMap;

    LinkedList<Tile> buildlist = new LinkedList<>();

    LinkedList<Tile> removelist = new LinkedList<>();

    LinkedList<PSExplosion> explosions = new LinkedList<PSExplosion>();

    ConsolePanel consolePanel;

    int state = 0;

    BaseIcon icon;

    FocusManager fm = new FocusManager(this);

    public int mine = 50;

    public int man = 4;

    public int manSpace = 10;

    String msg;

    int msgCount = 0;

    List<String> msgList = Collections.synchronizedList(new LinkedList<String>());

    int p[][] = null;

    final Random rand = new Random();

    private NetWorkManager netWorkManager;

    public GridMapRender(GridMap map){
        this.gridMap = map;

        p = new int[100][5];
        for (int j = 0; j < p.length; j++){
            p[j][4] = 0;
        }
    }

    public void splitTile(Tile tile){

        for (int n = 0; n < 20; n++){
            int z = rand.nextInt(p.length-1);
            p[z][0] = tileXToPx(tile.getTileX())-offsetX+16;
            p[z][1] = tileYToPx(tile.getTileY())-offsetY+16;
            p[z][2] = -25+rand.nextInt(50);
            p[z][3] = -10+rand.nextInt(20);
            p[z][4] = 25+rand.nextInt(25);
        }
    }

    public void draw(Graphics2D g){

        for (int y = 0; y < this.screenHeight/BG_HEIGHT + 1; ++y){
            for (int x = 0; x < this.screenWidth/BG_WIDTH+1; ++x){
                g.drawImage(ResourceManager.Constant.IMAGE_BG, x * BG_WIDTH, y * BG_HEIGHT,null);
            }
        }

        for (Tile s : gridMap.getTiles()){
            s.draw(g, offsetX, offsetY);
        }

        g.setColor(ResourceManager.Constant.GREEN);
        g.drawImage(ResourceManager.Constant.ICON_MINE,14,14,null);
        g.drawString(String.valueOf(mine),14 + 18, 14 + 10);
        g.drawImage(ResourceManager.Constant.ICON_MAN,16 + 50, 14, null);
        g.drawString(man + "/" + manSpace, 16 + 50 + 18, 14 + 10);

        if (msg != null){
            g.drawString(msg, (screenWidth-100)/2, (screenHeight - 100));
        }

        //fight
        for (int j = 0; j < p.length; j++){
            if( p[j][4] > 0){
                g.fillRect(p[j][0], p[j][1], 5, 5);
            }
        }

        for (PSExplosion explosion: explosions){
            explosion.draw(g, offsetX, offsetY);
        }
    }

    public void update(long elapsedTime){

        for (Tile s: gridMap.getTiles()){
            s.update(elapsedTime);
        }

        while (!buildlist.isEmpty()){
            Tile tile = buildlist.removeFirst();
            gridMap.add(tile);
        }

        while (!removelist.isEmpty()){
            Tile tile = removelist.removeFirst();
            gridMap.remove(tile);
        }

        if (msgCount <= 0){
            synchronized (msgList){
                msg = !msgList.isEmpty()?msgList.remove(0):null;
                msgCount = msg != null?150:0;
            }
        }else {
            --msgCount;
        }

        for (int j = 0; j < p.length; j++){
            if (p[j][4] > 0){
                p[j][0] += p[j][2];
                p[j][1] += p[j][3];
                p[j][4]--;
            }
        }

        for (int i = 0; i < explosions.size(); ++i){
            PSExplosion explosion = explosions.get(i);
            if (!explosion.update(elapsedTime)){
                explosions.remove(explosion);
            }
        }
    }

    public void addBuildTile(Tile tile){
        buildlist.add(tile);
    }

    public void addRemoveTile(Tile tile){
        removelist.add(tile);
        netWorkManager.remove(new SpriteInfo(tile.getUUID()));
    }

    public void addRemoveTileNetCallback(Tile tile){
        removelist.add(tile);
    }

    public void move(int tx, int ty){
        fm.move(tx + offsetX, ty + offsetY);
    }

    public void operate(int x, int y){
        fm.operate(x + offsetX, y + offsetY);
    }

    public void focus(int fx, int fy, int tx, int ty){
        fm.focus(fx + offsetX, fy + offsetY, tx + offsetX, ty + offsetY);
    }

    public void focus(int x, int y){
        fm.focus(x + offsetX, y + offsetY);
    }

    public void addMsg(String msg){
        msgList.add(msg);
    }

    public boolean checkResource(Resource resource){

        if (mine < resource.getMine()){
            addMsg(ResourceManager.Constant.MINE_ERROR);
            return false;
        }else{
            if (manSpace< (resource.getMan()+ man)){
                addMsg(ResourceManager.Constant.MAN_ERROR);
                return false;
            }else{
                this.man += resource.getMan();
            }
            this.mine -= resource.getMine();
        }
        return true;
    }

    public static Point tileToPx(int mapx, int mapy){

        int screenX = TILE_WIDTH * mapx;
        int screenY = TILE_HEIGHT * mapy;
        return new Point(screenX, screenY);
    }

    public static int tileXToPx(int x){
        return TILE_WIDTH * x;
    }

    public static int tileYToPx(int y){
        return TILE_HEIGHT * y;
    }

    public static int pxToTileX(float x){
        return Math.round(x) / TILE_WIDTH;
    }

    public static int pxToTileY(float y){
        return Math.round(y) / TILE_HEIGHT;
    }

    public static Point pxTolTile(float x, float y){

        int mapx = Math.round(x) / TILE_WIDTH;
        int mapy = Math.round(y) / TILE_HEIGHT;

        return new Point(mapx, mapy);

    }

    public GridMap getGridMap(){
        return gridMap;
    }

    public void setConsolePanel(ConsolePanel consolePanel){
        this.consolePanel = consolePanel;
        int x = GridMapRender.pxToTileX(offsetX);
        int y = GridMapRender.pxToTileY(offsetY);
        this.getConsolePanel().map_panel.setConsoleRectLocation(x, y);
    }

    public int getCurrentType(){
        return type;
    }

    public Tile getCurrentTile(){
        return fm.getCurrentTile();
    }

    public ConsolePanel getConsolePanel(){
        return consolePanel;
    }

    public Headquarter getHeadquarter(int type){

        for (Tile tile: gridMap.getTiles()){
            if (tile instanceof Headquarter && ((Headquarter)tile).getType() == type){
                return (Headquarter) tile;
            }
        }
        return null;
    }

    public void setNetWorkManager(NetWorkManager client){
        this.netWorkManager = client;
        fm.netWorkManager = this.netWorkManager;
    }

    public FocusManager getFm(){
        return fm;
    }

    public void setOffset(int offsetX, int offsetY){
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public NetWorkManager getNetWorkManager(){
        return netWorkManager;
    }

    public void addFocusSprite(Sprite sprite){
        fm.addFocusSprite(sprite);
    }

    public void blur(){
        fm.blur();
    }

    public void addExplosions(float x, float y){
        this.explosions.add(new PSExplosion(x,y));
    }

}
