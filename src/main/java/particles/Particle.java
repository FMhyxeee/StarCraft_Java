package particles;

import java.awt.*;

public class Particle {

    private Vector position;

    private Vector velocity;

    private int life;

    private Color color;

    public Particle(Vector pos, Vector velocity,Color col, int life){

        this.position = pos;

        this.velocity = velocity;

        this.color = col;

        if (life > 0){
            this.life = life;
        }
    }

    public void update(long elapsedTime){

    }

}
