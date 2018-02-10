package pacman;

import core.agents.Agent;
import core.misc.Config;
import core.misc.Environment;
import core.misc.SMA;

import java.awt.*;
import java.util.List;

/**
 * Agent defender qui donne des bonus
 */
public class DefenderAgent extends Agent {
    /**
     * tick number
     */
    private int tickNumber;
    /**
     * activation
     */
    private boolean isActive;
    /**
     * temps de vie
     */
    private int lifetime;
    /**
     * coordonnees disponibles
     */
    private List<Point> availableCoord;
    /**
     * environnement
     */
    private EnvironmentPacman environment;

    /**
     * Constructeur de l'agent Defender
     * 
     * @param environment
     * @param posX
     * @param posY
     * @param availableCoord
     */
    public DefenderAgent(Environment environment, int posX, int posY, List<Point> availableCoord) {
        super(environment, posX, posY);
        
        this.tickNumber=0;
        this.availableCoord = availableCoord;
        this.environment = (EnvironmentPacman) environment;
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
    	if (this.environment.isEndedGame()) {
    		return;
    	}
    	
    	 if(isActive && lifetime % Config.getLifeDefender() == 0) {
             setActive(false);
         }
    	
        if(tickNumber % Config.getTimeAppear() == 0) {
            this.setActive(true);
            lifetime = 0;
        }

        tickNumber++; 
        lifetime++;
    }

    /**
     * getter activation
     * 
     * @return activation
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * setter activation
     * 
     * @param active
     */
    public void setActive(boolean active) {
        isActive = active;
        if (active == false) {
            int rand = SMA.getRandom().nextInt(availableCoord.size());
            environment.moveAgentWithNewPos(this, availableCoord.get(rand).x, availableCoord.get(rand).y);
        }
    }
}
