package pacman;

import java.awt.Color;
import java.awt.Graphics;

import core.agents.Agent;
import core.misc.Environment;
import core.misc.SMA;
import core.view.Grid;

public class GridPacman extends Grid{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GridPacman(Environment environment, SMA sma) {
		super(environment, sma);
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        printDijkstra(g);
    }
	

    private void printDijkstra(Graphics g) {
        int[][] dijkstra = ((EnvironmentPacman)environment).getDijkstraResult();
        for(int x = 0 ; x < dijkstra.length ; x++) {
            for(int y = 0 ; y < dijkstra[x].length ; y++) {
            	g.setColor(Color.WHITE);
                g.drawString(dijkstra[x][y] + "", x * getZoomedBoxSize(), (y + 1) * getZoomedBoxSize());
            }
        }
    }
    
    protected boolean contitionToStop(Agent agent) {
    	if (agent instanceof DefenderAgent) {
    		DefenderAgent defender = ((DefenderAgent)agent);
    		if (! defender.isActive()){
    			return true;
    		}
    	}
    	
    	if (agent instanceof WinnerAgent) {
    		WinnerAgent winner = ((WinnerAgent)agent);
    		if (! winner.isActive()){
    			return true;
    		}
    	}
    	
    	return false;
    }

}
