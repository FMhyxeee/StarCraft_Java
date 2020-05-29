package tile;

import core.GridMap;
import core.GridMapRender;
import util.Resource;

import java.awt.*;
import java.util.LinkedList;

public class Marine extends Sprite {

    private static final Resource RESOURCE = new Resource(50, 1);

    public Marine(Animation[][] animations, int id) {
        super(animations, id);
    }

    @Override
    public float getSpeed() {
        return 0.02f;
    }

    @Override
    public Resource getResource() {
        return RESOURCE;
    }

    @Override
    public LinkedList<Point> move(float dx, float dy) {
        if (isReadFight() || isFighting()){
            stop();
            return super.move(dx, dy);
        }
        return super.move(dx, dy);
    }

    @Override
    public  void update(long elapsedTime) {
        super.update(elapsedTime);
        ainmStatus = 0;
        if (isFighting()){
            currentAnim.update(elapsedTime);
            target.setHealth(target.getHealth() - 0.001f + target.getDefence());
            if (target.getHealth() <= 0){
                currentAnim.currentIndex = 1;
                stop();
                gm.splitTile(target);
                gm.addRemoveTile(target);
            }
        }
    }


    @Override
    protected void adjustFight() {
        ainmStatus = 1;

        super.adjustFight();

        int targetX = this.target.getTileX();
        int targetY = this.target.getTileY();

        int width = Math.round(GridMapRender.TILE_WIDTH * target.getSize().x / 2.0f);
        int height = Math.round(GridMapRender.TILE_HEIGHT * target.getSize().y / 2.0f);

        super.gm.addExplosions(GridMapRender.tileXToPx(targetX) + width, GridMapRender.tileYToPx(targetY) + height);

    }


}
