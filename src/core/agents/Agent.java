package core.agents;


import core.misc.Environment;
import core.misc.Logger;

import java.awt.*;

public abstract class Agent {
    private int posX;
    private int posY;
    private Color color;
    private boolean hasChanged;
    private boolean isSelected = false;

    protected Environment environment;

    public Agent(Environment environment, int posX, int posY) {
        this.environment = environment;
        this.posX = posX;
        this.posY = posY;
    }

    public void decide() {
        hasChanged = false;
    }

    private void setChanged(boolean hasChanged) {
        this.hasChanged = hasChanged;
    }

    protected void hasChanged() {
        this.hasChanged = true;
    }

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
        if (isSelected) {
            return Color.BLUE;
        }
        if(color == null) {
            return Color.GRAY;
        }

        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    protected void logAgent() {
        if (hasChanged) {
            Logger.log(this.toString());
        }
    }

    @Override
    public String toString() {
        return "Agent;" + posY + ";" + posY;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}