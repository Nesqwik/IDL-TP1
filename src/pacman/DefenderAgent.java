package pacman;

import core.agents.Agent;
import core.misc.Config;
import core.misc.Environment;
import core.misc.SMA;

import java.awt.*;
import java.util.List;

public class DefenderAgent extends Agent {
    private int tickNumber;
    private boolean isActive;
    private int lifetime;
    private List<Point> availableCoord;

    public DefenderAgent(Environment environment, int posX, int posY, List<Point> availableCoord) {
        super(environment, posX, posY);
        
        this.tickNumber=0;
        this.availableCoord = availableCoord;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
        if (active == false) {
            int rand = SMA.getRandom().nextInt(availableCoord.size());
            environment.moveAgentWithNewPos(this, availableCoord.get(rand).x, availableCoord.get(rand).y);
        }
    }
}
