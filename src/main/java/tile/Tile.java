package tile;

import core.GridMap;

import java.awt.*;
import java.io.Serializable;


/**
*-----------------------------------
* @author hyx
* @date 2020/5/20 16:59
* @description 物体细节定义接口
*-----------------------------------
**/
public interface Tile extends Serializable {

    //选中
    public void focus();
    //取消
    public void blur();
    //克隆
    public Tile clone(int x, int y , GridMap gridMap);
    //画
    public void draw(Graphics2D g, int offsetX, int offsetY);
    //更新
    public void update(long elapsedTime);
    //X坐标
    public int getTileX();
    //Y坐标
    public int getTileY();
    //得到类型吗？
    public int getType();
    //得到ID
    public int getId();
    //得到大小
    public Point getSize();
    //设置血量
    public void setHealth(float health);
    //得到血量
    public float getHealth();
    //是否进行过操作
    public boolean opreate(int x, int y, String uuid);
    //不知道干什么的，拿到护盾吗？防御？
    public float getDefence();
    //设置唯一标识
    public void setUUID(String i);
    //得到唯一ID
    public String getUUID();

}
