package net.socket;

import java.io.Serializable;

/**
* @author hyx
* @description 事件类，其中action = 1 表示删除
*
**/
public class SpriteInfo implements Serializable {

    String UUID;

    int action;

    public SpriteInfo(String uuid){
        super();
        UUID = uuid;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
