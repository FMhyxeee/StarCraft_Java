package net.datagram;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Objects;

/**
*-----------------------------------
* @author hyx
* @date 2020/5/22 16:22
* @description 玩家信息类
*-----------------------------------
**/
public class PlayerInfo implements java.io.Serializable{

    public boolean isComputer;

    public boolean isRemove;

    private String serverName;

    private InetSocketAddress socketAddress;

    private Integer type;

    /**
     * 行为，1表示删除，2表示选中
     */
    private int action;

    public  PlayerInfo(String serverName, SocketAddress socketAddress, Integer type){
        super();

        this.serverName  = serverName;
        this.socketAddress = (InetSocketAddress)socketAddress;
        this.type = type;
    }

    public PlayerInfo(String serverName, SocketAddress socketAddress){
        this(serverName,(InetSocketAddress) socketAddress,null);
    }

    public PlayerInfo(String serverName){
        this(serverName,null, null);
    }

    public PlayerInfo(String serverName, Integer type, boolean isComputer){
        super();
        this.isComputer = isComputer;
        this.serverName = serverName;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof PlayerInfo)) {
            return false;
        }
        PlayerInfo that = (PlayerInfo) o;
        return Objects.equals(serverName, that.serverName) &&
                Objects.equals(socketAddress, that.socketAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverName, socketAddress);
    }

    public String getServerName(){
        return serverName;
    }

    public void setServerName(String serverName){
        this.serverName = serverName;
    }

    public InetSocketAddress getSocketAddress() {
        return socketAddress;
    }

    public void setSocketAddress(SocketAddress socketAddress) {
        this.socketAddress = (InetSocketAddress) socketAddress;
    }

    public void setSocketAddress(InetAddress address, int port){
        this.socketAddress = new InetSocketAddress(address,port);
    }

    public InetAddress getAdress(){
        return socketAddress.getAddress();
    }

    public Integer getType(){
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public boolean isComputer() {
        return isComputer;
    }

    public boolean isRemove() {
        return isRemove;
    }

    public void setComputer(boolean computer) {
        isComputer = computer;
    }

    public void setRemove(boolean remove) {
        isRemove = remove;
    }

    @Override
    public String toString() {
        return "PlayerInfo{" +
                "serverName='" + serverName + '\'' +
                '}';
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

}
