package core;

import icon.BaseIcon;
import tile.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
*-----------------------------------
* @author hyx
* @date 2020/5/20 17:00
* @description 一个GridMap，将Grid放入其中
*-----------------------------------
**/
public class GridMap {

    //Grid 地图
    private Grid[][] map;
    // 宽 高
    private int width, height;
    // tile容器，一个同步的容器
    private List<Tile> tiles = Collections.synchronizedList(new ArrayList<Tile>());
    //地图渲染器？
    private GridMapRender tileMapRender;
    //图标 Map
    private Map<String, BaseIcon> iconMap;
    //总数
    private long count;

    //注意宽和高在矩形里面的关系
    public GridMap(int width, int height){
        this.width = width;
        this.height = height;
        map = new Grid[height][width];
        for (int y = 0; y < height; ++y){
            for (int x = 0; x < width; ++x){
                map[y][x] = new Grid();
            }
        }
    }

    //Get allowed Set unallowed
    public int getHeight(){return height;}

    public int getWidth(){return width;}

    //删除tile,拿到一个，删除一片
    public void remove(Tile tile){
        if (tile == null) return;

        tiles.remove(tile);
        Point local = new Point(tile.getTileX(), tile.getTileY());
        Point size = tile.getSize();
        for (int y = local.y; y < local.y + size.y; ++y){
            for (int x = local.x; x < local.x + size.x; ++x){
                map[y][x].remove(tile);
            }
        }
    }

    //同步添加，确保顺序
    public synchronized void add(Tile tile){
        Point local = new Point(tile.getTileX(),tile.getTileY());
        Point size = tile.getSize();
        for (int y = local.y; y < local.y + size.y; ++y){
            for (int x = local.x; x < local.x + size.x; ++x){
                map[y][x].add(tile);
            }
        }
        if (tile.getUUID() == null){
            tile.setUUID(String.valueOf(++count));
        }
    }

    public List<Tile> getTiles(){return tiles;}

    public Grid getGrid(int tileX, int tileY){return map[tileY][tileX];}

    public Tile getTile(int tileX, int tileY){return map[tileY][tileX].isEmpty()?null:map[tileY][tileX].getFirst();}

    public Tile getTile(String UUID){
        for (Tile tile: tiles){
            if (tile.getUUID().equals(UUID)){
                return tile;
            }
        }
        return null;
    }

    //判断Grid是否有tile占用
    public boolean contains(int x, int y){
        return (x<0||y<0||!map[y][x].isEmpty());
    }

    //判断路径是否被阻挡
    public boolean isHit(int x, int y){
        if (!map[y][x].isEmpty()){
            Tile tile = map[y][x].getFirst();
            if (!(tile instanceof Sprite)){
                return true;
            }
        }
        return false;
    }




}
