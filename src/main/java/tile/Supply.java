package tile;

import util.Resource;

import java.awt.*;
/**
* @author hyx
* @description Supply提供者
*
**/
public class Supply extends House {

    private static final float BUILD_SPEED = 0.0005f;

    private static final Resource RESOURCE = new Resource(100,0);

    public Supply(Image[] images, int id) {
        super(images,id);
        this.currentImage = this.images[2];
    }

    @Override
    public boolean build(long elapsedTime){

        health = Math.min(1, health += elapsedTime * BUILD_SPEED);
        return health >= 1;
    }

    private final static Point SIZE = new Point(2, 2);

    @Override
    public Point getSize(){
        return SIZE;
    }

    @Override
    public Resource getResource() {
        return RESOURCE;
    }

    @Override
    public float getDefence() {
        return 0.0007f;
    }
}
