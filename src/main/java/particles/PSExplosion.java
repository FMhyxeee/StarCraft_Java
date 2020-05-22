package particles;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;



public class PSExplosion {
    private static int DEFAULT_NUM_PARTICLES = 2;

    private static int PARTICLES_MAX_LEFT = 15;

    private static Color color = new Color(230,100,30);

    private static Color color2 = new Color(230,200,30);

    private Random rand = new Random();

    protected ArrayList particles = new ArrayList();

    protected Vector pos;

    public PSExplosion(float x, float y){
        this.pos = new Vector(x,y,0);
        for (int i = 0; i < DEFAULT_NUM_PARTICLES; i++){
            particles.add(generateParticle());
        }
    }

    protected Paricle
}
