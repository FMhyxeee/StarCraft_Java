package net.socket;

import net.datagram.IconInfo;
import net.datagram.MoveInfo;
import net.datagram.PlayerInfo;
import net.datagram.PlayerList;
import net.datagram.SpriteInfo;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements Runnable {

    private Socket socket;

    private ClientListener clientListener;

    private PlayerInfo clientInfo;

    private int port;

    private boolean loop = true;

    public Client(int port) {
        super();
        this.port = port;
    }

    @Override
    public void run() {
        try {
            listen();
        }catch (Exception e){
            e.printStackTrace();
            this.clientListener.onClose(null);
        }
    }

    private void listen() throws Exception{

        while (loop){
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Object obj = in.readObject();
            System.out.println("Client.listen" + obj);
            if (obj instanceof PlayerInfo){
                PlayerInfo player = (PlayerInfo) obj;

                if (player.getAction() == 2){
                    clientListener.selectPlayer(new ClientEvent(player));
                }

                if (player.isRemove()){
                    clientListener.delPlayer(new ClientEvent(player));
                } else {
                    clientListener.addPlayer(new ClientEvent(player));
                }

                if (clientInfo.equals(player)){
                    clientListener.onJoinServer(new ClientEvent(player));
                }

            }else if(obj instanceof PlayerList){

                PlayerList player = (PlayerList) obj;
                clientListener.onStartGame(new ClientEvent(player));

            }else if(obj instanceof MoveInfo){

                MoveInfo moveInfo = (MoveInfo) obj;
                clientListener.onMove(new ClientEvent(moveInfo));

            }else if(obj instanceof IconInfo){

                IconInfo moveInfo = (IconInfo) obj;
                if(moveInfo.getAction()==1){
                    clientListener.onBuild(new ClientEvent(moveInfo));
                }else{
                    clientListener.onReadyBuild(new ClientEvent(moveInfo));
                }

            }else if(obj instanceof SpriteInfo){
                SpriteInfo moveInfo = (SpriteInfo) obj;
                if (moveInfo.getAction() == 1){
                    clientListener.onRemoveTile(new ClientEvent(moveInfo));
                }
            }
        }
    }

    public void move(MoveInfo moveInfo){
        send(moveInfo);
    }

    public void remove(SpriteInfo moveInfo){
        send(moveInfo);
    }

    public void build(IconInfo moveInfo){
        send(moveInfo);
    }

    public void readyBuild(IconInfo iconInfo){
        send(iconInfo);
    }

    public void send(Object info){

        try{
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(PlayerInfo clientInfo, PlayerInfo serverInfo){
        try{
            loop = true;
            socket = new Socket(serverInfo.getAdress(), port);
            clientInfo.setSocketAddress(socket.getLocalSocketAddress());

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(clientInfo);
        }catch (Exception e){
            e.printStackTrace();
        }

        this.clientInfo = clientInfo;
        new Thread(this).start();
    }

    public void addClientListener(ClientListener clientListener){
        this.clientListener = clientListener;
    }

    public void close(){
        System.out.println("Client.close()");
        try {
            socket.close();
            loop = false;
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
