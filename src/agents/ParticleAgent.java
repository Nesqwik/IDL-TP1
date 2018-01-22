package agents;

import misc.Environment;

import java.awt.*;

public class ParticleAgent extends Agent {

    protected int pasX;
    protected int pasY;

    public ParticleAgent(Environment environment, int posX, int posY, int pasX, int pasY) {
        super(environment, posX, posY);

        this.setColor(Color.GRAY);
        this.setPasX(pasX);
        this.setPasY(pasY);
    }

    private boolean isCollideX(Agent[][] moore) {
        return moore[pasX + 1][1] instanceof FrontierAgent;
    }

    private boolean isCollideY(Agent[][] moore) {
        return moore[1][pasY + 1] instanceof FrontierAgent;
    }

    protected void onCollide(ParticleAgent otherAgent, Agent[][] moore) {
        this.setColor(Color.RED);
        otherAgent.setColor(Color.RED);
        this.hasChanged();
    }

    @Override
    public void decide() {
        super.decide();
        Agent[][] moore = environment.getMoore(this);
        this.setColor(Color.GRAY);

        if(pasX == 0 && pasY == 0) {
            return;
        }

        if (moore[pasX + 1][pasY + 1] instanceof ParticleAgent) {
            ParticleAgent otherAgent = (ParticleAgent) moore[pasX + 1][pasY + 1];
            this.onCollide(otherAgent, moore);
        } else if (pasX != 0 || pasY != 0) {
            if (this.isCollideX(moore)) {
                this.setPasX(-this.getPasX());
                this.hasChanged();
            }

            if (this.isCollideY(moore)) {
                this.setPasY(-this.getPasY());
                this.hasChanged();
            }

            if (moore[pasX + 1][pasY + 1] instanceof ParticleAgent) {
                ParticleAgent otherAgent = (ParticleAgent) moore[pasX + 1][pasY + 1];
                this.onCollide(otherAgent, moore);
            } else {
                this.environment.moveAgent(this, pasX, pasY);
            }
        }

        logAgent();
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

    @Override
    public String toString() {
        return "ParticleAgent;" + this.hashCode() + ";" + this.getPosX() + ";" + this.getPosY() + ";" + this.getPasX() + ";" + this.getPasY();
    }
}
