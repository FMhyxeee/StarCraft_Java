package net.udp;

import net.datagram.PlayerInfo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Iterator;
import java.util.List;

public class Detector extends PlayerProcessor{

    public Detector(PlayerContext context) {
        super(context);
    }

    @Override
    protected void process() {

        List<PlayerInfo> serverList = context.getPlayerInfos();

        DatagramSocket detectorSocket = context.getDetectorSocket();

        while (loop){
            synchronized (serverList){
                Iterator<PlayerInfo> i = serverList.iterator();

                while (i.hasNext()){

                    PlayerInfo playerInfo = i.next();
                    String serverName = playerInfo.getServerName();
                    DatagramPacket packer = new DatagramPacket(serverName.getBytes(),serverName.getBytes().length);
                    packer.setSocketAddress(playerInfo.getSocketAddress());

                    try{
                        detectorSocket.send(packer);
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                    try{
                        detectorSocket.receive(packer);
                    }catch (IOException e){
                        System.out.println(e.getMessage());
                        i.remove();
                        context.remove(playerInfo);
                    }

                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void close(){
        loop=false;
    }
}
