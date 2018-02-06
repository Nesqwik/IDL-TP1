package pacman;

import java.awt.Color;

import core.agents.Agent;
import core.misc.Environment;

public class WinnerAgent extends Agent {
	
	private boolean isActive;

    public WinnerAgent(Environment environment, int posX, int posY) {
        super(environment, posX, posY);
        isActive = false;
    }


    @Override
    public Color getColor() {
        return Color.green;
    }

    @Override
    public int getShape() {
        return Agent.SQUARE;
    }

    @Override
    public void onDestroyed() {

    }
    
    public void activate() {
    	isActive = true;
    }
    
    public boolean isActive() {
    	return isActive;
    }

}
