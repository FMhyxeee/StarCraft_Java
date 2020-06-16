package tile;

import util.Resource;

import java.awt.*;

public class Barrack extends House {

    private static final float BUILD_SPEED = 0.0002f;

    public Barrack(Image[] images, int id) {
        super(images, id);
        this.currentImage = this.images[2];
    }

    @Override
    public boolean build(long elapsedTime) {
        health  = Math.min(1, health += elapsedTime * BUILD_SPEED);
        return false;
    }

    @Override
    public Point getSize() {
        return null;
    }

    @Override
    public Resource getResource() {
        return null;
    }

    @Override
    public float getDefence() {
        return 0;
    }
}
