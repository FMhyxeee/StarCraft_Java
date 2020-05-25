package net.udp;

/**
* @author hyx
* @description Player监听接口
*
**/


public interface PlayerListener {


    public void join(PlayerEvent e);

    public void quit(PlayerEvent e);
}
