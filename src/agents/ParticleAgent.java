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

    private boolean isCollideX(Agent[][] moore) {
        return moore[pasX + 1][1] instanceof FrontierAgent;
    }

    private boolean isCollideY(Agent[][] moore) {
        return moore[1][pasY + 1] instanceof FrontierAgent;
    }

    private void checkCollideAndAct(Agent[][] moore) {
        if(this.isCollideX(moore)) {
            this.setPasX(- this.getPasX());
        }

        if(this.isCollideY(moore)) {
            this.setPasY(- this.getPasY());
        }
    }

    @Override
    public void decide() {
        Agent[][] moore = environment.getMoore(this);
        this.setColor(Color.GRAY);

        if(pasX == 0 && pasY == 0) {
            // Do nothing
            return;
        } else if (moore[pasX + 1][pasY + 1] instanceof ParticleAgent) {
            this.setColor(Color.RED);
            ParticleAgent otherAgent = (ParticleAgent) moore[pasX + 1][pasY + 1];
            otherAgent.setColor(Color.RED);
            this.sendPas(otherAgent);
        }

        this.checkCollideAndAct(moore);
        this.environment.moveAgent(this, pasX, pasY);
    }

    private void sendPas(ParticleAgent other) {
        int lastPasX = other.getPasX();
        int lastPasY = other.getPasY();

        other.setPasX(this.getPasX());
        other.setPasY(this.getPasY());

        this.setPasX(lastPasX);
        this.setPasY(lastPasY);
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
