package net.udp;

import net.datagram.PlayerInfo;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Receiver extends PlayerProcessor {

    private DatagramPacket datagramPacket = new DatagramPacket(new byte[255],255);

    private boolean loop = true;

    public Receiver(PlayerContext context) {
        super(context);
    }

    private void listen(){
        try{
            DatagramSocket datagramSocket = context.getDatagramSocket();

            datagramSocket.receive(datagramPacket);
            String msg = new String(datagramPacket.getData(), datagramPacket.getOffset(),datagramPacket.getLength());
            String serverName = context.getServerName();

            if ("request".equals(msg)){
                if (serverName != null){
                    DatagramPacket send = new DatagramPacket(serverName.getBytes(), serverName.length());
                    send.setSocketAddress(datagramPacket.getSocketAddress());
                    datagramSocket.send(send);
                }
            } else {
                PlayerInfo serverInfo = new PlayerInfo(msg, datagramPacket.getSocketAddress(), null);
                if (!msg.equals(serverName) && !context.contains(serverInfo)){
                    context.add(serverInfo);
                } else if (!datagramPacket.getSocketAddress().equals(
                        datagramSocket.getLocalSocketAddress())){
                    datagramSocket.send(datagramPacket);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void process() {
        while (loop){
            listen();
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void close(){ loop = false;}
}
