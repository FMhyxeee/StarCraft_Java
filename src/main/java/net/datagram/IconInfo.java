package net.datagram;

public class IconInfo implements java.io.Serializable {

    String keyName;

    String tileUUId;

    String newTileUUId;

    //1为build，0为readBuild
    int action;

    public IconInfo(String tileUUId, String newTileUUId, String keyName){
        super();
        this.tileUUId = tileUUId;
        this.newTileUUId = newTileUUId;
        this.keyName = keyName;
    }

    public IconInfo(String tileUUId, String keyName){
        super();
        this.keyName = keyName;
        this.tileUUId = tileUUId;
    }

    public String getTileUUId(){
        return tileUUId;
    }

    public void setTileUUId(String tileUUId) {
        this.tileUUId = tileUUId;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getNewTileUUId() {
        return newTileUUId;
    }

    public void setNewTileUUId(String newTileUUId) {
        this.newTileUUId = newTileUUId;
    }
}

