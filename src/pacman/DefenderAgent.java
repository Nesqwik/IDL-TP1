package pacman;

import core.agents.Agent;
import core.misc.Environment;

import java.awt.*;

public class DefenderAgent extends Agent {
    private int tickNumber;
    private boolean isActive;
    private int lifetime;

    public DefenderAgent(Environment environment, int posX, int posY) {
        super(environment, posX, posY);
    }


    @Override
    public Color getColor() {
        return Color.yellow;
    }

    @Override
    public int getShape() {
        return Agent.ROUND;
    }

    @Override
    public void onDestroyed() {

    }

    @Override
    public void decide() {
        if(tickNumber % 10 == 0) {
            this.setActive(true);
            this.tickNumber = 0;
        }

        if(lifetime % 10 == 0) {
            this.setActive(false);
        }

        if(!isActive) {
            tickNumber++;
        } else {
            lifetime++;
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
