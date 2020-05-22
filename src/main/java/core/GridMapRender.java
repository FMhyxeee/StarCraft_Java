package core;

import tile.Tile;

import java.util.LinkedList;

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
}
