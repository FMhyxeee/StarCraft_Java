package net.socket;

import net.datagram.IconInfo;
import net.datagram.MoveInfo;
import net.datagram.PlayerInfo;
import net.datagram.SpriteInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ServerProcessor implements Runnable {

    private boolean loop = true;

    private Socket socket;

    private Server server;

    private PlayerInfo clientInfo;

    public ServerProcessor(Server server, Socket socket){
        super();
        this.socket = socket;
        this.server = server;
    }


    public void send(Object obj){
        try{
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() throws Exception{
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        Object obj = in.readObject();

        if(obj instanceof PlayerInfo){


            clientInfo =(PlayerInfo) obj;
            //把所在用户列表发送到当前用户
            for(PlayerInfo playerInfo:server.playerList){
                System.out.println("playerInfo:"+playerInfo.getServerName());
                send(playerInfo);

            }
            //把当前用户信息发送给所有的用户列表
            server.joinPlayer(clientInfo);
            server.playerList.add(clientInfo);


        }else if(obj instanceof MoveInfo){


            MoveInfo moveInfo =(MoveInfo) obj;
            server.batchSend(this,moveInfo);
            server.clientListener.onMove(new ClientEvent(moveInfo));

        }else if(obj instanceof IconInfo){

            IconInfo iconInfo =(IconInfo) obj;
            server.batchSend(this,iconInfo);
            if(iconInfo.getAction()==1){
                server.clientListener.onBuild(new ClientEvent(iconInfo));
            }else{
                server.clientListener.onReadyBuild(new ClientEvent(iconInfo));
            }

        }else if(obj instanceof SpriteInfo){

            SpriteInfo spriteInfo =(SpriteInfo) obj;
            server.batchSend(this,spriteInfo);
            if(spriteInfo.getAction()==1){
                server.clientListener.onRemoveTile(new ClientEvent(spriteInfo));
            }

        }

    }


    @Override
    public void run() {
        while (loop){
            try{
                listen();
            }catch (Exception e){
                e.printStackTrace();
                server.removeClient(this);
                break;
            }
        }
    }

    public void move(List<SpriteInfo> sprites, int tx, int ty){
        try{
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(new MoveInfo(sprites, tx, ty));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void close(){
        try{
            socket.close();
            loop = false;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void start(){
        new Thread(this).start();
    }

    public PlayerInfo getClientInfo(){
        return clientInfo;
    }
}
