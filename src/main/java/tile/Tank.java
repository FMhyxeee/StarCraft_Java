package tile;

import util.Resource;

import java.awt.*;
import java.util.LinkedList;

public class Tank extends Sprite {

    private static final Resource RESOURCE = new Resource(100,4);

    private final static Point SIZE = new Point(1,1);


    public Tank(Animation[][] animations, int id) {
        super(animations, id);
    }

    @Override
    public LinkedList<Point> move(float dx, float dy) {
        if (isReadFight() || isFighting()){
            stop();
            return super.move(dx, dy);
        }
        return super.move(dx,dy);
    }

    @Override
    public  void update(long elapsedTime) {
        super.update(elapsedTime);
        if (isFighting()){
            target.setHealth(target.getHealth() - 0.00008f);
            if (target.getHealth() <= 0){
                stop();
                gm.splitTile(target);
                gm.addRemoveTile(target);
            }
        }
    }

    @Override
    public float getSpeed() {
        return 0.012f;
    }

    @Override
    public Resource getResource() {
        return RESOURCE;
    }

    @Override
    public Point getSize() {
        return SIZE;
    }
}
