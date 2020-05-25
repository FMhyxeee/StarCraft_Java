package icon;

import core.GridMapRender;
import tile.House;

import java.awt.*;

public class HouseIcon extends BaseIcon {
    public HouseIcon(Image image) {
        super(image);
    }

    @Override
    public void onClicked(GridMapRender gridMapRender) {
        House house = (House) gridMapRender.getCurrentTile();
        if (gridMapRender.checkResource(resource)){
            house.readyBuild(this);
        }
    }

    @Override
    public float getBuildSpeed() {
        return BUILD_SPEED;
    }

    private static final float BUILD_SPEED = 0.0003f;
}
