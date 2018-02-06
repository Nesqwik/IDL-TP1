package pacman;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import core.agents.Agent;
import core.agents.FrontierAgent;
import core.misc.Config;
import core.misc.Environment;

public class AvatarAgent extends Agent implements KeyListener {
    final static int UP = 0;
    final static int DOWN = 1;
    final static int LEFT = 2;
    final static int RIGHT = 3;

    private int tickNumber = 0;
    
    private int invinsibleTime;
    private int nbDefender;

    private int pasX;
    private int pasY;
    private WinnerAgent winner;

    public AvatarAgent(Environment environment, int posX, int posY, WinnerAgent winner) {
        super(environment, posX, posY);
        this.invinsibleTime = Config.getInvinsibleTime();
        this.nbDefender = 0;
        this.winner = winner;
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
        
        if (winnerIsActivate() && agent instanceof WinnerAgent) {
        	this.environment.endGame();
        	return;
        }

        if (!(agent instanceof WallAgent) && !(agent instanceof FrontierAgent)) {
            moveAvatar();
        }
    }

	private void moveAvatar() {
		environment.moveAgent(this, pasX, pasY);
		environment.dijkstra(this);
	}
    
	private void vinsible() {
		this.setColor(Color.RED);
		this.environment.setPacmanInvinsible(false);
	}
	
    public void invinsible(DefenderAgent agent){
    	this.nbDefender++;
    	this.invinsibleTime = Config.getInvinsibleTime();
    	agent.setActive(false);
    	this.setColor(Color.PINK);
    	this.environment.setPacmanInvinsible(true);
    	if (winnerIsActivate()) {
    		winner.activate();
    	}
    }

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

    public void kill() {
        this.setAlive(false);
        this.environment.removeAgent(this);
    }
}
