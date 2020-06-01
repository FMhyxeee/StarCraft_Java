package tile;

import core.GridMapRender;
import core.ResourceManager;
import icon.BaseIcon;

import util.Resource;
import util.path.AStarSearch;


import java.awt.*;
import java.util.LinkedList;

public class Scv extends Sprite {

    private static final Resource RESOURCE = new Resource(50, 1);

    public Scv(Animation[][] animations, int id){
        super(animations,id);
    }

    public void build(int tx, int ty){

        this.status = 3;
        this.buildX = tx;
        this.buildY = ty;
        path = AStarSearch.findBuildPath(gridMap, x, y, buildX, buildY, icon.getTile(getType()).getSize());

    }

    @Override
    public void onArrive(){
        super.onArrive();

        if (isStartBuild()){
            Tile tile = icon.getTile(getType()).clone(GridMapRender.pxToTileX(buildX), GridMapRender.pxToTileY(buildY), gridMap);
            this.setUUID(this.newTileuuid);
            this.gridMap.getTileMapRender().addBuildTile(tile);
            this.buildTile = tile;
            this.status = 4;
            this.blur();

            if (tileX < GridMapRender.pxToTileX(buildX)){
                currentAnim = animations[0][EAST];
            }else if (tileX > GridMapRender.pxToTileX(buildX)){
                currentAnim = animations[0][WEST];
            }

            if(tileY<GridMapRender.pxToTileY(buildY)){
                currentAnim = animations[0][SOUTH];
            }else if(tileY>GridMapRender.pxToTileY(buildY)){
                currentAnim = animations[0][NORTH];
            }

            gm.getConsolePanel().work_panel.dispatch(this);
        }

        if (isReadMining()){
            mining();
        }

        if (isBackHQing()){
            if (getType() == gm.getCurrentType()){
                this.gridMap.getTileMapRender().mine += 8;
            }

            readMining(target, this.targetx, this.targety);
        }
    }

    @Override
    public void draw(Graphics2D g, int offsetX, int offsetY) {
        super.draw(g, offsetX, offsetY);

        if (isFighting()){
            int x = Math.round(this.x - offsetX);
            int y = Math.round(this.y - offsetY);

            int targetX = target.getTileX();
            int targetY = target.getTileY();

            if(targetX==tileX || targetY==tileY){
                if(tileX<targetX){
                    g.drawImage(ResourceManager.Constant.SCV_SPARK, x+18, y,null);
                }else if(tileX>targetX){
                    g.drawImage(ResourceManager.Constant.SCV_SPARK, x-18, y,null);
                }
                if(tileY<targetY){
                    g.drawImage(ResourceManager.Constant.SCV_SPARK, x, y+18,null);
                }else if(tileY>targetY){
                    g.drawImage(ResourceManager.Constant.SCV_SPARK, x, y-18,null);
                }
            }else{
                if(tileX<targetX){
                    if(tileY<targetY){
                        g.drawImage(ResourceManager.Constant.SCV_SPARK, x+15, y+18,null);
                    }else{
                        g.drawImage(ResourceManager.Constant.SCV_SPARK, x+15, y-18,null);
                    }

                }else{
                    if(tileY<targetY){
                        g.drawImage(ResourceManager.Constant.SCV_SPARK, x-15, y+18,null);
                    }else{
                        g.drawImage(ResourceManager.Constant.SCV_SPARK, x-15, y-18,null);
                    }
                }
            }
        }

        int type = this.getType();

        if (isReadyBuild() && type == gm.getCurrentType()){
            Point location = GridMapRender.pxTolTile(gm.moveX+gm.offsetX, gm.moveY+gm.offsetY);

            Point size = icon.getTile(type).getSize();

            for(int y=location.y;y<location.y+size.y;++y){
                for(int x = location.x;x<location.x+size.x;++x){
                    //check if is fit
                    if(gm.getGridMap().contains(x, y)){
                        g.setColor(Color.red);
                        Point position = GridMapRender.tileToPx(x, y);
                        g.fillRect(position.x-gm.offsetX, position.y-gm.offsetY, GridMapRender.TILE_WIDTH, GridMapRender.TILE_HEIGHT);
                    }else{
                        g.setColor(Color.green);
                        Point position = GridMapRender.tileToPx(x, y);
                        g.fillRect(position.x-gm.offsetX, position.y-gm.offsetY,GridMapRender.TILE_WIDTH,GridMapRender.TILE_HEIGHT);
                    }

                }
            }

            Point position = GridMapRender.tileToPx(location.x, location.y);
            g.drawImage(icon.getTileImage(type), position.x - gm.offsetX, position.y - offsetY, null);
        }

        if (isBuilding()){
            int x = Math.round(this.x-offsetX);
            int y = Math.round(this.y-offsetY);
            //调整光照效果的位置
            if(tileX<GridMapRender.pxToTileX(buildX)){
                g.drawImage(ResourceManager.Constant.SCV_SPARK, x+30, y,null);
            }else if(tileX>GridMapRender.pxToTileX(buildX)){
                g.drawImage(ResourceManager.Constant.SCV_SPARK, x-30, y,null);
            }
            if(tileY<GridMapRender.pxToTileY(buildY)){
                g.drawImage(ResourceManager.Constant.SCV_SPARK, x, y+15,null);
            }else if(tileY>GridMapRender.pxToTileY(buildY)){
                g.drawImage(ResourceManager.Constant.SCV_SPARK, x, y-15,null);
            }
        }

        if (isMining()){

            int x = Math.round(this.x-offsetX);
            int y = Math.round(this.y-offsetY);
            int targetX = target.getTileX();
            int targetY = target.getTileY();
            if(targetX==tileX || targetY==tileY){
                if(tileX<targetX){
                    g.drawImage(ResourceManager.Constant.SCV_SPARK, x+18, y,null);
                }else if(tileX>targetX){
                    g.drawImage(ResourceManager.Constant.SCV_SPARK, x-18, y,null);
                }
                if(tileY<targetY){
                    g.drawImage(ResourceManager.Constant.SCV_SPARK, x, y+18,null);
                }else if(tileY>targetY){
                    g.drawImage(ResourceManager.Constant.SCV_SPARK, x, y-18,null);
                }
            }else{
                if(tileX<targetX){
                    if(tileY<targetY){
                        g.drawImage(ResourceManager.Constant.SCV_SPARK, x+15, y+18,null);
                    }else{
                        g.drawImage(ResourceManager.Constant.SCV_SPARK, x+15, y-18,null);
                    }

                }else{
                    if(tileY<targetY){
                        g.drawImage(ResourceManager.Constant.SCV_SPARK, x-15, y+18,null);
                    }else{
                        g.drawImage(ResourceManager.Constant.SCV_SPARK, x-15, y-18,null);
                    }
                }
            }
        }
    }




    @Override
    public synchronized void update(long elapsedTime) {
        super.update(elapsedTime);
        //如果需要修房子
        if(isBuilding()){
            House house = (House)buildTile;
            if(house.build(elapsedTime)){
                this.status =0;
                //如果是房子的话就增加人口
                if(house instanceof Supply){
                    this.gm.manSpace+=8;
                }
            }
        }

        //如果需要采矿
        if(isMining()){
            Mine mine = (Mine)target;

            if(mine.mining(elapsedTime)){
                mine.reset();
                ainmStatus = 1;
                backHQ();
            }
        }

        if(isFighting()){
            target.setHealth(target.getHealth()-0.00095f+target.getDefence());
            if(target.getHealth()<=0){
                stop();
                gm.splitTile(target);
                gm.addRemoveTile(target);
            }
        }
    }

    protected void backHQ(){
        this.status = 9;
        Headquarter hq = gm.getHeadquarter(getType());
        if (hq != null){
            int dx = GridMapRender.tileXToPx(hq.getTileX());
            int dy = GridMapRender.tileYToPx(hq.getTileY());
            super.move(dx, dy);
        }
    }

    protected boolean isBackHQing(){
        return status == 9;
    }

    public boolean isReadyBuild(){
        return status == 2;
    }

    public boolean isStartBuild(){
        return this.status == 3;
    }

    public boolean isBuilding(){
        return this.status == 4;
    }

    float targetx, targety;

    public void readMining(Tile tile, float x, float y){
        if (isBuilding()){

        }else {
            this.target = tile;
            ainmStatus = 0;
            this.status = 1;
            this.targetx = x;
            this.targety = y;
            super.move(x,y);
        }
    }

    @Override
    public void fight(Tile target, int x, int y) {
        if (isBuilding()){

        } else {
            super.fight(target, x, y);
        }
    }

    protected void mining(){
        status = 7;

        int targetX = this.target.getTileX();
        int targetY = this.target.getTileY();

        if (targetX == tileX || targetY == tileY){

            if(tileX<targetX){
                currentAnim = animations[0][EAST];
            }else if(tileX>targetX){
                currentAnim = animations[0][WEST];
            }
            if(tileY<targetY){
                currentAnim = animations[0][SOUTH];
            }else if(tileY>targetY){
                currentAnim = animations[0][NORTH];
            }
        } else {
            if (tileX < targetX){
                currentAnim = tileY<targetY?animations[0][SOUTH_EAST]:animations[0][NORTH_EAST];
            } else {
                currentAnim = tileY<targetY?animations[0][SOUTH_WEST]:animations[0][NOTH_WEST];
            }
        }
    }

    protected  boolean isReadMining(){
        return status == 1;
    }

    protected boolean isMining(){
        return status == 7;
    }


    String newTileuuid;

    @Override
    public boolean opreate(int tx, int ty, String newTileuuid) {

        if (isReadyBuild()){

            this.newTileuuid = newTileuuid;
            int type = getType();
            Point location = GridMapRender.pxTolTile(tx, ty);
            Point size = icon.getTile(type).getSize();

            for(int y=location.y;y<location.y+size.y;++y){
                for(int x = location.x;x<location.x+size.x;++x){
                    //check if is fit
                    if(gm.getGridMap().contains(x, y)){
                        gm.addMsg(ResourceManager.Constant.BUILD_ERROR);
                        return true;
                    }
                }
            }
            build(tx,ty);
            return true;
        }

        return false;
    }

    public void readyBuild(BaseIcon icon){
        this.status = 2;
        this.icon = icon;
    }

    @Override
    public LinkedList<Point> move(float dx, float dy) {
        if (isReadyBuild()){
            stop();
            gm.mine += icon.getResource().getMine();
            return null;
        }

        if (isBuilding()){
            return null;
        }

        if (isStartBuild()){
            stop();
            return super.move(dx,dy);
        }

        if (isReadMining() || isMining() || isBackHQing()){
            stop();
            return super.move(dx, dy);
        }

        if (isReadFight() || isFighting()){
            stop();
            return super.move(dx, dy);
        }

        return super.move(dx, dy);
    }

    @Override
    public float getSpeed() {
        return 0.016f;
    }

    @Override
    public Resource getResource() {
        return RESOURCE;
    }


}
