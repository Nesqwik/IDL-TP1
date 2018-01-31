package pacman;

import core.agents.Agent;
import core.misc.Environment;

import java.awt.*;

public class WallAgent extends Agent {

    public WallAgent(Environment environment, int posX, int posY) {
        super(environment, posX, posY);
    }

    @Override
    public int getShape() {
        return Agent.SQUARE;
    }

    @Override
    public Color getColor() {
        return Color.BLACK;
    }

    @Override
    public void onDestroyed() {

    }

}
