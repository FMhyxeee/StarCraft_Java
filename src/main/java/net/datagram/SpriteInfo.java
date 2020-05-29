package net.datagram;

import java.io.Serializable;

public class SpriteInfo implements Serializable {

    String UUID;

    int action;

    public SpriteInfo(String uuid){
        super();
        UUID = uuid;
    }

    public String getUUID(){
        return UUID;
    }

    public void setUUID(String uuid){
        UUID = uuid;
    }

    public int getAction(){
        return action;
    }

    public void setAction(int action){
        this.action = action;
    }
}
