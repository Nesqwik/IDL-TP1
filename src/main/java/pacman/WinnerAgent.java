package pacman;

import java.awt.Color;

import core.agents.Agent;
import core.misc.Environment;

/**
 * Agent qui ouvre la porte avant de gagner
 */
public class WinnerAgent extends Agent {
	
	/**
	 * activation
	 */
	private boolean isActive;

    /**
     * Constructeur de l'agent qui ouvre la porte
     * 
     * @param environment
     * @param posX
     * @param posY
     */
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
    
    /**
     * active l'agent
     */
    public void activate() {
    	isActive = true;
    }
    
    /**
     * getter activation
     * 
     * @return vrai si l'agent est actif
     */
    public boolean isActive() {
    	return isActive;
    }

}
