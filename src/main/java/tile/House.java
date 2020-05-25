package tile;

import core.Grid;
import core.GridMap;
import core.GridMapRender;
import core.ResourceManager;
import gui.WorkPanel;
import icon.BaseIcon;
import net.datagram.IconInfo;
import util.path.AStarNode;
import util.path.AStarSearch;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class House extends AbstractTile implements Builder{

    protected Image currentImage;

    protected Image[] images;

    protected int status = 0;

    protected BaseIcon icon;

    protected float complete;


    public House(int id) {
        super(id);
    }

    public House(Image[] images, int id){
        super(id);
        this.images = images;
    }

    @Override
    public void draw(Graphics2D g, int offsetX, int offsetY){
        int x = Math.round(this.x-offsetX);
        int y = Math.round(this.y-offsetY);
        if(isSelected()){

            if(getType()!=gridMap.getTileMapRender().getCurrentType()){
                g.setColor(Color.RED);
                g.drawArc(x-Math.round(getWidth()*0.27f), y , Math.round(getWidth()*1.5f), getHeight() , 0, 360);
            }else{
                g.setColor(ResourceManager.Constant.GREEN);
                g.drawArc(x-Math.round(getWidth()*0.27f), y , Math.round(getWidth()*1.5f), getHeight() , 0, 360);
            }

            g.fillRect(x, y + getHeight()+5,  Math.round(getWidth()*(health)), 3);
            g.setColor(Color.black);
            g.drawRect(x, y + getHeight()+5, getWidth(), 3);
        }

        g.drawImage(currentImage, x, y, null);
    }

    protected void building(long elapsedTime){
        if (status == 1){
            complete += elapsedTime * icon.getBuildSpeed();
            if (complete >= 1){
                Tile tile = icon.getTile(getType());
                GridMapRender gm = gridMap.getTileMapRender();
                Point location = AStarSearch.findNeighborNode(gridMap, x, y);
                tile = tile.clone(location.x, location.y, gridMap);
                tile.setUUID(java.util.UUID.randomUUID().toString());
                gm.addBuildTile(tile);
                complete = 0;
                status = 0;
                tile.setHealth(1.0f);
                gm.getConsolePanel().work_panel.build(null);
                gm.getNetWorkManager().build(new IconInfo(getUUID(),tile.getUUID(),gridMap.getIconKey(icon)));
            }
        }
    }

    @Override
    public void update(long elapsedTime) {
        if (health < 0.6){
            this.currentImage = this.images[2];
        }
        else if (health < 0.98){
            this.currentImage = this.images[1];
        }
        else {
            this.currentImage = this.images[0];
        }
        building(elapsedTime);
    }

    @Override
    public Tile clone(int x, int y, GridMap gridMap) {
        Constructor constructor = getClass().getConstructors()[0];
        try {
            House house = (House) constructor.newInstance(new Object[]{images,id});
            house.x = x * GridMapRender.TILE_WIDTH;
            house.y = y * GridMapRender.TILE_HEIGHT;
            house.tileX = x;
            house.tileY = y;
            house.gridMap = gridMap;
            house.gm = gridMap.getTileMapRender();
            return house;

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getHeight(){
        return currentImage.getHeight(null);
    }

    public int getWidth(){
        return currentImage.getWidth(null);
    }

    @Override
    public void readyBuild(BaseIcon icon) {
        WorkPanel workPanel
    }
}
