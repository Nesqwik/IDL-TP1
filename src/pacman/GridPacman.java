package pacman;

import java.awt.Color;
import java.awt.Graphics;

import core.agents.Agent;
import core.misc.Config;
import core.misc.Environment;
import core.misc.SMA;
import core.view.Grid;

/**
 * Grille pacman
 */
public class GridPacman extends Grid{
	
	/**
	 * serial
	 */
	private static final long serialVersionUID = 1L;
	
	private EnvironmentPacman environment;

	/**
	 * Constructeur de la grille pacman
	 * 
	 * @param environment
	 * @param sma
	 */
	public GridPacman(Environment environment, SMA sma) {
		super(environment, sma);
		
		this.environment = (EnvironmentPacman)environment;
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (Config.isPrintDijkstra()) {
        	printDijkstra(g);
        }
    }
	

    /**
     * Imprime dijkstra
     * 
     * @param g
     */
    private void printDijkstra(Graphics g) {
        int[][] dijkstra = ((EnvironmentPacman)environment).getDijkstraResult();
        for(int x = 0 ; x < dijkstra.length ; x++) {
            for(int y = 0 ; y < dijkstra[x].length ; y++) {
            	g.setColor(Color.WHITE);
                g.drawString(dijkstra[x][y] + "", x * getZoomedBoxSize(), (y + 1) * getZoomedBoxSize());
            }
        }
    }
    
    
    /**
     * V�rifie si on imprime l'agent
     * 
     * @param agent cible
     * @return vrai si on l'arr�te sinon faux
     */
    protected boolean hideAgent(Agent agent) {
    	if (agent instanceof DefenderAgent) {
    		DefenderAgent defender = ((DefenderAgent)agent);
    		if (! defender.isActive()){
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    @Override
    protected void printAgents(Graphics g, Environment environment, int wdOfRow, int htOfRow) {
        super.printAgents(g, environment, wdOfRow, htOfRow);

        WinnerAgent winner = this.environment.getWinner();
        if (winner.isActive()) {
        	printAgent(winner, g, wdOfRow, htOfRow);
        }
    }

}
