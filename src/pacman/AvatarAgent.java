package pacman;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import core.agents.Agent;
import core.agents.FrontierAgent;
import core.misc.Config;
import core.misc.Environment;

/**
 * Agent avatar
 */
public class AvatarAgent extends Agent implements KeyListener {
    /**
     * constante pour monter
     */
    final static int UP = 0;
    /**
     * constante pour descendre
     */
    final static int DOWN = 1;
    /**
     * constante pour aller a gauche
     */
    final static int LEFT = 2;
    /**
     * constante pour aller à droite
     */
    final static int RIGHT = 3;

    /**
     * nombre de tick
     */
    private int tickNumber = 0;
    
    /**
     * temps de visibilité
     */
    private int invinsibleTime;
    /**
     * nombre de defender
     */
    private int nbDefender;

    /**
     * pas x
     */
    private int pasX;
    /**
     * pas y
     */
    private int pasY;
    /**
     * environnement
     */
    private EnvironmentPacman environment;

    /**
     * Constructeur de l'agent Avatar
     * 
     * @param environment
     * @param posX
     * @param posY
     */
    public AvatarAgent(Environment environment, int posX, int posY) {
        super(environment, posX, posY);
        this.invinsibleTime = Config.getInvinsibleTime();
        this.nbDefender = 0;
        this.environment = (EnvironmentPacman) environment;
    }

    @Override
    public int getShape() {
        return Agent.ROUND;
    }

    @Override
    public Color getColor() {
        return Color.RED;
    }

    @Override
    public void onDestroyed() {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void decide() {
    	if (this.environment.isEndedGame()) {
    		return;
    	}
    	
        tickNumber++;
        
        if (tickNumber % Config.getSpeedAvatar() != 0) {
            return;
        }
        invinsibleTime--;
        if (invinsibleTime == 0) {
        	vinsible();
        }

        Agent[][] moore = environment.getMoore(this);
        Agent agent = moore[pasX + 1][pasY + 1];
        
        if (agent instanceof DefenderAgent) {
        	DefenderAgent defender = (DefenderAgent)moore[pasX + 1][pasY + 1];
        	invinsible(defender);
        }
        
        if (verifyWin()) {
        	return;
        }

        if (!(agent instanceof WallAgent) && !(agent instanceof FrontierAgent)) {
            moveAvatar();
        }
    }

	/**
	 * Verifie si l'avatar a gagné
	 * 
	 * @return vrai si l'avatar a gagné
	 */
	private boolean verifyWin() {
		int nextX = environment.isToric() ? (this.getPosX() + environment.getCols() + pasX) % environment.getCols() : this.getPosX() + pasX;
        int nextY = environment.isToric() ? (this.getPosY() + environment.getRows() + pasY) % environment.getRows() : this.getPosY() + pasY;
        if (nextX >= 0 && nextX < this.environment.getCols() && nextY >= 0 && nextY < this.environment.getRows() && this.environment.canWin(nextX, nextY)) {
        	this.environment.endGame();
        	return true;
        }
        return false;
	}

	/**
	 * Déplace l'avatar
	 */
	private void moveAvatar() {
		environment.moveAgent(this, pasX, pasY);
		environment.dijkstra(this);
	}
    
	/**
	 * Rend l'avatar vinsible
	 */
	private void vinsible() {
		this.setColor(Color.RED);
		this.environment.setPacmanInvinsible(false);
	}
	
    /**
     * Rend l'avatar invinsible
     * 
     * @param agent defender
     */
    public void invinsible(DefenderAgent agent){
    	this.nbDefender++;
    	this.invinsibleTime = Config.getInvinsibleTime();
    	agent.setActive(false);
    	this.setColor(Color.PINK);
    	this.environment.setPacmanInvinsible(true);
    	if (winnerIsActivate()) {
    		this.environment.activateWinner();
    	}
    }

	/**
	 * Vérifie si l'agent gagnant a été activé
	 * 
	 * @return vrai si oui sinon faux
	 */
	private boolean winnerIsActivate() {
		return this.nbDefender >= 4;
	}

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP:
                //direction = UP;
                pasX = 0;
                pasY = -1;
                break;

            case KeyEvent.VK_DOWN:
                //direction = DOWN;
                pasX = 0;
                pasY = 1;
                break;

            case KeyEvent.VK_LEFT:
                //direction = LEFT;
                pasX = -1;
                pasY = 0;
                break;

            case KeyEvent.VK_RIGHT:
                //direction = RIGHT;
                pasX = 1;
                pasY = 0;
                break;
        }
        
        if (!environment.isStartedGame()) {
        	environment.startGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    /**
     * supprime l'agent
     */
    public void kill() {
        this.setAlive(false);
        this.environment.removeAgent(this);
    }
}
