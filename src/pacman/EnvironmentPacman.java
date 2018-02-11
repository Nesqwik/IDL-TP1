package pacman;

import java.util.LinkedList;
import java.util.Queue;

import core.agents.Agent;
import core.misc.Environment;

/**
 * Environnement pacman
 */
public class EnvironmentPacman extends Environment {

    /**
     * d�but du jeu
     */
    protected boolean startGame;
    /**
     * fin du jeu
     */
    protected boolean endGame;
    /**
     * pacman invainsible
     */
    protected boolean pacmanInvinsible;

    /**
     * r�sultat du dijkstra
     */
    private int[][] dijkstraResult;
    
    /**
     * winner
     */
    private WinnerAgent winner;
    
    /**
     * Constructeur de l'environnement Pacman
     */
    public EnvironmentPacman() {
    	super();
        this.startGame = false;
        this.endGame = false;

        dijkstraResult = new int[cols][rows];
    }

    /**
     * Voisin Dijkstra � traiter
     */
    private class DijkstraVoisin {
        /**
         * position x
         */
        public int x;
        /**
         * position y
         */
        public int y;
        /**
         * valeur
         */
        public int val;
        /**
         * Constructeur du voisin Dijkstra a traiter
         * 
         * @param x 
         * @param y
         * @param val
         */
        public DijkstraVoisin(int x, int y, int val) {
            this.x = x;
            this.y = y;
            this.val = val;
        }
    }

    /**
     * Calcule Dijkstra
     * 
     * @param agent cible
     */
    public void dijkstra(Agent agent) {
        int gridX = agent.getPosX();
        int gridY = agent.getPosY();

        for (int x = 0; x < dijkstraResult.length; x++) {
            for (int y = 0; y < dijkstraResult[x].length; y++) {
                dijkstraResult[x][y] = -2;
            }
        }

        Queue<DijkstraVoisin> voisins = new LinkedList<>();
        markValue(gridX, gridY, 0, voisins);

        while(voisins.size() != 0) {
            DijkstraVoisin p = voisins.poll();

            for(int x = 0 ; x < 3 ; x++) {
                for (int y = 0; y < 3; y++) {
                    if ((x + y) % 2 == 1) {
                        int posX = this.getRealPosX(p.x + (x - 1));
                        int posY = this.getRealPosY(p.y + (y - 1));

                        if (posX >= 0 && posX < dijkstraResult.length
                                && posY >= 0 && posY < dijkstraResult[posX].length
                                && dijkstraResult[posX][posY] == -2) {
                            markValue(posX, posY, p.val, voisins);
                        }
                    }
                }
            }
        }
    }

    /**
     * Enregistre la valeur sur la case
     * 
     * @param x
     * @param y
     * @param value
     * @param voisins
     * @return faux s'il s'agit d'un mur sinon vrai
     */
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

    /**
     * r�cup�re les r�sultats du Dijkstra
     * 
     * @return les r�sultat du Dijkstra
     */
    public int[][] getDijkstraResult() {
        return dijkstraResult;
    }

    /**
     * getter de la valeur de dijkstra dans une case
     * 
     * @param x
     * @param y
     * @return la valeur dans la grille de Dijkstra
     */
    public int getDijkstraPos(int x, int y) {
        if (x < 0 || y < 0 || x > dijkstraResult.length - 1 || y > dijkstraResult[0].length - 1) {
            return -1;
        }
        return dijkstraResult[x][y];
    }
    
    /**
     * d�marrer le jeu
     */
    public void startGame() {
    	this.startGame = true;
    }
    
    /**
     * terminer le jeu
     */
    public void endGame() {
    	this.endGame = true;
    }
    
    /**
     * getter de startGame
     * 
     * @return vrai si le jeu a commenc�
     */
    public boolean isStartedGame() {
    	return this.startGame;
    }
    
    /**
     * getter de endGame
     * 
     * @return vrai si le jeu est termin�
     */
    public boolean isEndedGame() {
    	return this.endGame;
    }
 
    /**
     * setter pacman invinsible
     * 
     * @param invinsible
     */
    public void setPacmanInvinsible(boolean invinsible) {
    	this.pacmanInvinsible = invinsible;
    }
    
    /**
     * getter pacman invinsible
     * 
     * @return pacman invinsible
     */
    public boolean isPacmanInvinsible(){
    	return this.pacmanInvinsible;
    }

	/**
	 * getter winner
	 * 
	 * @return winner
	 */
	public WinnerAgent getWinner() {
		return winner;
	}

	/**
	 * setter winner
	 * 
	 * @param winner
	 */
	public void setWinner(WinnerAgent winner) {
		this.winner = winner;
	}
    
    /**
     * Activate winner
     */
    public void activateWinner() {
    	this.winner.activate();
    }
    
    /**
     * Affirme si l'agent peut gagner
     * 
     * @param posX
     * @param posY
     * @return vrai si l'argent peut gagner
     */
    public boolean canWin(int posX, int posY) {
    	return (winner.isActive() && winner.getPosX() == posX && winner.getPosY() == posY);
    }
    
}
