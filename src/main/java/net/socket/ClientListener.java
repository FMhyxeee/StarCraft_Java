package net.socket;

public interface ClientListener {

    public void addPlayer(ClientEvent e);

    public void delPlayer(ClientEvent e);

    public void selectPlayer(ClientEvent e);

    public void onJoinServer(ClientEvent e);

    public void onEstablishServer(ClientEvent e);

    public void onStartGame(ClientEvent e);

    public void onMove(ClientEvent e);

    public void onReadyBuild(ClientEvent e);

    public void onBuild(ClientEvent e);

    public void onClose(ClientEvent e);

    public void onRemoveTile(ClientEvent e);

}
