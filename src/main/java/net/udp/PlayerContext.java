package net.udp;

import net.datagram.PlayerInfo;
import net.datagram.PlayerList;

import javax.xml.crypto.Data;
import java.net.DatagramSocket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PlayerContext {

    private String serverName;

    private DatagramSocket datagramSocket;

    private DatagramSocket detectorSocket;

    private PlayerListener playerListener;

    private List<PlayerInfo> playerInfos = Collections.synchronizedList(new LinkedList<PlayerInfo>());

    public DatagramSocket getDatagramSocket(){
        return datagramSocket;
    }

    public DatagramSocket getDetectorSocket(){
        return detectorSocket;
    }

    public String getServerName(){
        return serverName;
    }

    public void setServerName(String serverName){
        this.serverName = serverName;
    }

    public boolean contains(PlayerInfo playerInfo){
        return playerInfos.contains(playerInfo);
    }

    public void add(PlayerInfo playerInfo){

        playerInfos.add(playerInfo);
        playerListener.join(new PlayerEvent(playerInfo));
    }

    public void remove(PlayerInfo playerInfo){
        playerListener.quit(new PlayerEvent(playerInfo));
    }

    public void addPlayerListener(PlayerListener playerListener){
        this.playerListener = playerListener;
    }

    public List<PlayerInfo> getPlayerInfos(){
        return playerInfos;
    }

    public PlayerListener getPlayerListener(){
        return playerListener;
    }

    public void setDatagramSocket(DatagramSocket datagramSocket){
        this.datagramSocket = datagramSocket;
    }

    public void setDetectorSocket(DatagramSocket detectorSocket){
        this.detectorSocket = detectorSocket;
    }

}
