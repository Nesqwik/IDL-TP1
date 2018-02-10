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
     * début du jeu
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
     * résultat du dijkstra
     */
    private int[][] dijkstraResult;
    
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
     * Voisin Dijkstra à traiter
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

        Queue<DijkstraVoisin> queue = new LinkedList<>();
        markValue(gridX, gridY, 0, queue);

        dijkstraRecursive(queue);
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
     * traitement du dijkstra
     * 
     * @param voisins la file des voisins à traiter
     */
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

    /**
     * récupère les résultats du Dijkstra
     * 
     * @return les résultat du Dijkstra
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
     * démarrer le jeu
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
     * @return vrai si le jeu a commencé
     */
    public boolean isStartedGame() {
    	return this.startGame;
    }
    
    /**
     * getter de endGame
     * 
     * @return vrai si le jeu est terminé
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
    
}
