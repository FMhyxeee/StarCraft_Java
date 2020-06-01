package net.udp;

public abstract class PlayerProcessor implements Runnable {

    protected PlayerContext context;

    protected boolean loop = true;

    public PlayerProcessor(PlayerContext context){
        super();
        this.context = context;
    }

    public void start(){
        loop = true;
        new Thread(this).start();
    }

    @Override
    public void run() {
        process();
    }

    protected abstract void process();
}
