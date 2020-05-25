package tile;

import icon.BaseIcon;

public interface Builder {

    public void readyBuild(BaseIcon icon);

    public Boolean isBuilding();

    public float getComplete();
}
