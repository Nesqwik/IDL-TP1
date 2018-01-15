package agents;


import misc.Environment;

import java.awt.*;

public abstract class Agent {
    private int posX;
    private int posY;
    private Color color;

    protected Environment environment;

    public Agent(Environment environment, int posX, int posY) {
        this.environment = environment;
        this.posX = posX;
        this.posY = posY;
    }

    public abstract void decide();

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
