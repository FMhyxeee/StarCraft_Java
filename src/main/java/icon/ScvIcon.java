package icon;

import core.GridMapRender;
import net.datagram.IconInfo;
import tile.Scv;

import java.awt.*;

public class ScvIcon extends BaseIcon {
    public ScvIcon(Image image) {
        super(image);
    }

    @Override
    public void onClicked(GridMapRender gridMapRender) {
        Scv tile = (Scv) gridMapRender.getCurrentTile();
        if (gridMapRender.checkResource(resource)){
            gridMapRender.getNetWorkManager().readyBuild(new IconInfo(tile.getUUID(), gridMapRender.getGridMap().getIconKey(this)));
        }
    }
}
