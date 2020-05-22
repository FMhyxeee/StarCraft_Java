package tile;

import core.GridMap;
import core.GridMapRender;
import core.ResourceManager;

import java.awt.*;

/**
* @author hyx
* @description Tile的抽象基类
*
**/
public abstract class AbstractTile implements Tile {
    //使用protect 防止直接被继承
    protected int id;

    protected float x,y;

    protected int tileX, tileY;

    protected boolean selected;

    protected float health;

    protected GridMap gridMap;

    protected GridMapRender gm;

    protected String UUID;

    public AbstractTile(int id){
        this.id = id;
    }

    public boolean isSelected(){
        return selected;
    }


    @Override
    public void focus() {
        selected = true;
    }

    @Override
    public void blur() {
        selected = false;
    }

    @Override
    public abstract Tile clone(int x, int y, GridMap gridMap);

    @Override
    public abstract void draw(Graphics2D g, GridMap gridMap);

    @Override
    public void update(long elapsedTime) {

    }

    @Override
    public int getTileX() {
        return tileX;
    }

    @Override
    public int getTileY() {
        return tileY;
    }

    @Override
    public int getType() {
        return id/ ResourceManager.Constant.TYPE_SIZE;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point getSize() {
        return null;
    }

    @Override
    public void setHealth(float health) {
        this.health = health;
    }

    @Override
    public float getHealth() {
        return this.health;
    }

    @Override
    public boolean opreate(int x, int y, String uuid) {
        return false;
    }


    @Override
    public void setUUID(String uuid) {
        UUID = uuid;
    }

    @Override
    public String getUUID() {
        return UUID;
    }
}
