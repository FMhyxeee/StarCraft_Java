package core;

import net.NetWorkManager;
import tile.Mine;
import tile.Scv;
import tile.Sprite;
import tile.Tile;
import util.path.AStarNode;
import util.path.AStarSearch;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class FocusManager {

    GridMapRender gm;

    Tile tile;

    NetWorkManager netWorkManager;

    private LinkedList<Sprite> sprites = new LinkedList<>();

    public FocusManager(GridMapRender gm){
        super();
        this.gm = gm;
    }

    public void focus(int x, int y){
        int tileX = GridMapRender.pxToTileX(x);
        int tileY = GridMapRender.pxToTileY(y);
        focus(gm.gridMap.getGrid(tileX,tileY));
    }

    public void focus(int fx, int fy, int tx, int ty){

        Point fromTile = GridMapRender.pxTolTile(fx,fy);
        Point toTile = GridMapRender.pxTolTile(tx,ty);
        List<Tile> tiles = getTiles(fromTile,toTile,gm.getCurrentType());
        focus(tiles);
    }

    public void operate(int x, int y){

        Tile tile = getCurrentTile();
        String newTileUUID = UUID.randomUUID().toString();
        if (tile == null || !tile.opreate(x,y,newTileUUID)){
            focus(x,y);
        }
        netWorkManager.operate(tile, x, y, newTileUUID);

    }

    public void operate(Tile tile, int x, int y, String newTileUUID){
        if (tile != null){
            tile.opreate(x,y,newTileUUID);
        }
    }

    public void move(LinkedList<Sprite> sprites, int tx, int ty){

        if (sprites.size() > 0){
            Point target = new Point(tx, ty);
            Tile tile = gm.gridMap.getTile(GridMapRender.pxToTileX(tx),GridMapRender.pxToTileY(ty));

            if (tile != null){
                LinkedList<AStarNode> fightNodes = AStarSearch.findFightPath(gm.gridMap, tile);
                for (int i = 0; i < sprites.size(); ++i){

                    Sprite sprite = sprites.get(i);
                    if (sprite == null){
                        continue;
                    }

                    if (tile instanceof Mine && sprite instanceof Scv){
                        Scv scv = (Scv) sprite;
                        if (!fightNodes.isEmpty()){
                            AStarNode node = fightNodes.removeFirst();
                            scv.readMining(tile, node.getX(), node.getY());
                        } else {
                            scv.move(tx, ty);
                        }
                    }else if (tile.getType() != sprite.getType()){
                        if (!fightNodes.isEmpty()){
                            AStarNode node = fightNodes.removeFirst();
                            sprite.fight(tile,node.getX(),node.getY());
                        }else {
                            sprite.move(tx, tx);
                        }
                    }
                }
            }
        }else if (tile == null){
            LinkedList<AStarNode> space = AStarSearch.findSpace(gm.gridMap, tx, ty, sprites.size());
            for (int i = 0; i < space.size() && i < space.size(); ++i){
                Sprite sprite = sprites.get(i);
                AStarNode node = space.get(i);
                if (sprite != null){
                    sprite.move(node.getX(), node.getY());
                }
            }
        }
    }

    public void move(int tx, int ty){
        move(sprites, tx, ty);
        netWorkManager.move(sprites, tx, ty);
    }

    private void focus(List<Tile> tiles){
        if (tiles.size() == 1){
            blur();
            tile = tiles.get(0);
            tile.focus();

            if (tile.getType() == gm.getCurrentType()){
                gm.getConsolePanel().work_panel.dispatch(tile);
                if (tiles instanceof Sprite){
                    sprites.add((Sprite) tile);
                }
            }
        }else if (tiles.size() > 1){
            blur();
            for (Tile t : tiles) {
                if (t instanceof Sprite){
                    t.focus();
                    sprites.add((Sprite) t);
                }
            }
        }
    }

    public List<Tile> getTiles(Point fromTile, Point toTile, int type){

        List<Tile> result = new ArrayList<>();
        for (int y = fromTile.y; y <= toTile.y; ++y){
            for (int x = fromTile.x; x <= toTile.x; ++x){
                for (Tile tile : gm.gridMap.getGrid(x, y)){
                    if (tile.getType() == type){
                        result.add(tile);
                    }
                }
            }
        }
        return result;
    }

    public void blur() {
        gm.getConsolePanel().work_panel.dispatch(null);
        if (tile != null){
            this.blur();
            tile = null;
        }

        for (Tile tile : sprites){
            tile.blur();
        }

        sprites.clear();
    }

    public Tile getCurrentTile() {
        return sprites.isEmpty() ? tile:sprites.getFirst();
    }

    public LinkedList<Sprite> getSprites(){
        return sprites;
    }

    public void addFocusSprite(Sprite sprite){
        sprites.add(sprite);
    }






}
