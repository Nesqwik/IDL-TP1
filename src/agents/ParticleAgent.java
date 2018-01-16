package agents;

import misc.Environment;

import java.awt.*;

public class ParticleAgent extends Agent {

    private int pasX;
    private int pasY;

    public ParticleAgent(Environment environment, int posX, int posY, int pasX, int pasY) {
        super(environment, posX, posY);

        this.setColor(Color.GRAY);
        this.setPasX(pasX);
        this.setPasY(pasY);
    }

    @Override
    public void decide() {
        Agent[][] moore = environment.getMoore(this);

        for(int x = 0 ; x < moore.length ; x++) {
            for(int y = 0 ; y < moore[x].length ; y++) {
                System.out.println("x : " + x + " - y : " + y + " - val:" + moore[x][y]);
            }
        }

        environment.moveAgent(this, pasX, pasY);
        System.out.println("-----------------------------");

    }

    public int getPasX() {
        return pasX;
    }

    public void setPasX(int pasX) {
        this.pasX = pasX;
    }

    public int getPasY() {
        return pasY;
    }

    public void setPasY(int pasY) {
        this.pasY = pasY;
    }
}
