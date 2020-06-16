package net;

import net.datagram.IconInfo;
import net.datagram.PlayerInfo;
import net.datagram.PlayerList;
import net.datagram.SpriteInfo;
import net.socket.ClientListener;
import net.udp.Player;
import net.udp.PlayerListener;
import tile.Sprite;
import tile.Tile;
import util.RandomSequence;

import java.util.ArrayList;
import java.util.List;

public class MockNetWorkManager implements NetWorkManager {

    ArrayList<String> computers = new ArrayList<>();

    @Override
    public void select(String name, int index) {
        if (name.equals("Computer")){
            computers.add(name);
        }
    }

    @Override
    public PlayerList startGame() {
        if (computers.size() < 1){
            return null;
        }

        List<Integer> players = RandomSequence.generate(computers.size() + 1);

        return new PlayerList(players.get(computers.size()-1),players);
    }

    @Override
    public void build(IconInfo iconInfo) {
    }

    @Override
    public void addClientListener(ClientListener clientListener) {
    }

    @Override
    public void addPlayerListener(PlayerListener playerListener) {
    }

    @Override
    public void closeListen() {
    }

    @Override
    public void establishServer(String serverName) {
    }

    @Override
    public void join(PlayerInfo clientInfo, PlayerInfo serverInfo) {
    }

    @Override
    public void listen() {
    }

    @Override
    public void move(List<Sprite> sprites, int tx, int ty) {
    }

    @Override
    public void readyBuild(IconInfo iconInfo) {
    }

    @Override
    public void close() {
    }

    @Override
    public void operate(Tile tile, int tx, int ty, String newTileUUID) {
    }

    @Override
    public void remove(SpriteInfo spriteInfo) {
    }

}
