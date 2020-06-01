package net;

import net.datagram.IconInfo;
import net.datagram.PlayerInfo;
import net.datagram.PlayerList;
import net.datagram.SpriteInfo;
import net.socket.ClientListener;
import net.udp.PlayerListener;
import tile.Sprite;
import tile.Tile;

import java.util.List;

/**
* @author hyx
* @description 网络管理类，每一个JstarCraft都会对应一个NetManager
*
**/

public interface NetWorkManager {


    public void establishServer(String serverName);

    public void join(PlayerInfo clientInfo, PlayerInfo serverInfo);

    public void listen();

    public void closeListen();

    public void select(String name, int index);

    public PlayerList startGame();

    public void addPlayerListener(PlayerListener playerListener);

    public void addClientListener(ClientListener clientListener);

    public void move(List<Sprite> sprites, int tx, int ty);

    public void operate(Tile tile, int tx, int ty, String newTileUUID);

    public void build(IconInfo iconInfo);

    public void readyBuild(IconInfo iconInfo);

    public void remove(SpriteInfo spriteInfo);

    public void close();






}

