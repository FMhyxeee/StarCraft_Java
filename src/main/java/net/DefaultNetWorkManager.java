package net;

import net.datagram.*;
import net.socket.Client;
import net.socket.ClientListener;
import net.socket.Server;
import net.udp.Player;
import net.udp.PlayerListener;
import tile.Sprite;
import tile.Tile;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class DefaultNetWorkManager implements NetWorkManager{


    private static final int SOCK_PORT = 8389;

    private static final int DATAGRAM_PORT = 8380;

    private static final int DETECTOR_PORT = 8381;

    Player player;

    Server server;

    Client client;


    public DefaultNetWorkManager(String ip) throws SocketException{
        player = new Player(ip, DATAGRAM_PORT, DETECTOR_PORT);
        server = new Server(SOCK_PORT);
        client = new Client(SOCK_PORT);
    }

    @Override
    public void establishServer(String serverName) {
        player.establishServer(serverName);
        server.establish(serverName);
    }

    @Override
    public void join(PlayerInfo clientInfo, PlayerInfo serverInfo) {
        client.start(clientInfo,serverInfo);
    }

    @Override
    public void listen() {
        player.listen();
    }

    @Override
    public void closeListen() {
        player.close();
    }

    @Override
    public void select(String name, int index) {
        if (server.isEstablish()){
            server.select(name, index);
        }
    }

    @Override
    public PlayerList startGame() {
        PlayerList playerList = server.startGame();
        return playerList.getPlayers().size()==1?null:playerList;
    }

    @Override
    public void addPlayerListener(PlayerListener playerListener) {
        player.addPlayerListener(playerListener);
    }

    @Override
    public void addClientListener(ClientListener clientListener) {
        server.addClientListener(clientListener);
        client.addClientListener(clientListener);
    }

    @Override
    public void move(List<Sprite> sprites, int tx, int ty) {
        List<SpriteInfo> spriteInfos = new ArrayList<>();
        for (Sprite sprite : sprites){
            spriteInfos.add(new SpriteInfo(sprite.getUUID()));
        }

        MoveInfo moveInfo = new MoveInfo(spriteInfos, tx, ty);

        if (server.isEstablish()){
            server.move(moveInfo);
        } else {
            client.move(moveInfo);
        }
    }

    @Override
    public void operate(Tile tile, int tx, int ty, String newTileUUID) {
        if (tile == null){
            return;
        }

        MoveInfo info = new MoveInfo(new SpriteInfo(tile.getUUID()), tx, ty, newTileUUID);

        if (server.isEstablish()){
            server.move(info);
        } else {
            client.move(info);
        }
    }

    @Override
    public void build(IconInfo iconInfo) {
        iconInfo.setAction(1);
        if (server.isEstablish()){
            server.build(iconInfo);
        } else {
            client.build(iconInfo);
        }
    }

    @Override
    public void readyBuild(IconInfo iconInfo) {
        if (server.isEstablish()){
            server.readyBuild(iconInfo);
        } else{
            client.readyBuild(iconInfo);
        };
    }

    @Override
    public void remove(SpriteInfo spriteInfo) {
        spriteInfo.setAction(1);
        if (server.isEstablish()){
            server.remove(spriteInfo);
        } else {
            client.remove(spriteInfo);
        }
    }

    @Override
    public void close() {
        closeListen();
        if (server.isEstablish()){
            server.close();
        } else {
            client.close();
        }
    }
}
