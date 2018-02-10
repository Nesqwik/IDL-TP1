package pacman;

import java.util.LinkedList;
import java.util.Queue;

import core.agents.Agent;
import core.misc.Environment;

public class EnvironmentPacman extends Environment {

    protected boolean startGame;
    protected boolean endGame;
    protected boolean pacmanInvinsible;

    private int[][] dijkstraResult;
    
    public EnvironmentPacman() {
    	super();
        this.startGame = false;
        this.endGame = false;

        dijkstraResult = new int[cols][rows];
    }

    private class DijkstraVoisin {
        public int x;
        public int y;
        public int val;
        public DijkstraVoisin(int x, int y, int val) {
            this.x = x;
            this.y = y;
            this.val = val;
        }
    }

    public void dijkstra(Agent agent) {
        int gridX = agent.getPosX();
        int gridY = agent.getPosY();

        for (int x = 0; x < dijkstraResult.length; x++) {
            for (int y = 0; y < dijkstraResult[x].length; y++) {
                dijkstraResult[x][y] = -2;
            }
        }

        Queue<DijkstraVoisin> queue = new LinkedList<>();
        markValue(gridX, gridY, 0, queue);

        dijkstraRecursive(queue);
    }

    private boolean markValue(int x, int y, int value, Queue<DijkstraVoisin> voisins) {
        if (!(grid[x][y] instanceof WallAgent)) {
            dijkstraResult[x][y] = value;
            voisins.add(new DijkstraVoisin(x, y, value + 1));
            return true;
        } else {
            dijkstraResult[x][y] = -1;
            return false;
        }
    }

    public void dijkstraRecursive(Queue<DijkstraVoisin> voisins) {
        while(voisins.size() != 0) {
            DijkstraVoisin p = voisins.poll();
            
            int xMoinsUn = isToric ? (p.x - 1 + this.cols) % this.cols : p.x - 1;
			int xPlusUn = isToric ? (p.x + 1) % this.cols : p.x + 1;
			int yMoinsUn = isToric ? (p.y - 1 + this.rows) % this.rows : p.y - 1;
			int yPlusUn = isToric ? (p.y + 1) %this.rows : p.y + 1;

            if (xMoinsUn >= 0 && dijkstraResult[xMoinsUn][p.y] == -2) {
                markValue(xMoinsUn, p.y, p.val, voisins);
            }

            if (xPlusUn < dijkstraResult.length  && dijkstraResult[xPlusUn][p.y] == -2) {
                markValue(xPlusUn, p.y, p.val, voisins);
            }

            if (yMoinsUn >= 0 && dijkstraResult[p.x][yMoinsUn] == -2) {
                markValue(p.x, yMoinsUn, p.val, voisins);
            }

            if (yPlusUn < dijkstraResult[p.x].length && dijkstraResult[p.x][yPlusUn] == -2) {
                markValue(p.x, yPlusUn, p.val, voisins);
            }
        }
    }

    public int[][] getDijkstraResult() {
        return dijkstraResult;
    }

    public int getDijkstraPos(int x, int y) {
        if (x < 0 || y < 0 || x > dijkstraResult.length - 1 || y > dijkstraResult[0].length - 1) {
            return -1;
        }
        return dijkstraResult[x][y];
    }
    
    public void startGame() {
    	this.startGame = true;
    }
    
    public void endGame() {
    	this.endGame = true;
    }
    
    public boolean isStartedGame() {
    	return this.startGame;
    }
    
    public boolean isEndedGame() {
    	return this.endGame;
    }
 
    public void setPacmanInvinsible(boolean invinsible) {
    	this.pacmanInvinsible = invinsible;
    }
    
    public boolean isPacmanInvinsible(){
    	return this.pacmanInvinsible;
    }
    
}
