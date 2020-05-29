package tile;

import com.sun.org.apache.regexp.internal.RE;
import util.Resource;

import java.awt.*;

public class Headquarter extends House {

    private static final float BUILD_SPEED = 0.0003f;

    private static final Resource RESOURCE = new Resource(400, 0);

    public Headquarter(Image[] images, int id) {
        super(images, id);
        this.currentImage = this.images[0];
    }

    @Override
    public void update(long elapsedTime) {
        super.building(elapsedTime);
    }

    @Override
    public boolean build(long elapsedTime) {
        health = Math.min(1, health += elapsedTime * BUILD_SPEED);
        return health >= 1;
    }

    private final static Point SIZE = new Point(3, 3);
    @Override
    public Point getSize() {
        return SIZE;
    }

    @Override
    public Resource getResource() {
        return RESOURCE;
    }

    @Override
    public float getDefence() {
        return 0.0009f;
    }
}
