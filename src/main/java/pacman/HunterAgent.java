package pacman;

import core.agents.Agent;
import core.misc.Config;
import core.misc.Environment;

import java.awt.*;

/**
 * Acteur chasseur
 */
public class HunterAgent extends Agent {
	/**
	 * pas x
	 */
	private int pasX;
	/**
	 * pasY
	 */
	private int pasY;
	/**
	 * nombre de tick
	 */
	private int nbTick = 0;
	/**
	 * environnement
	 */
	private EnvironmentPacman environment;

	/**
	 * Constructeur de l'agent
	 * 
	 * @param environment
	 * @param posX
	 * @param posY
	 */
	public HunterAgent(Environment environment, int posX, int posY) {
		super(environment, posX, posY);
		
		this.environment = (EnvironmentPacman) environment;
	}

	@Override
	public int getShape() {
		return Agent.TRIANGLE;
	}

	@Override
	public Color getColor() {
		return Color.GREEN;
	}

	@Override
	public void onDestroyed() {

	}

	/**
	 * bouge le chasseur
	 */
	private void moveHunter() {
		this.pasX = 0;
		this.pasY = 0;

		if (!environment.isStartedGame()) {
			return;
		}

		int lastValue;

		if (!environment.isPacmanInvinsible()) {
			lastValue = Integer.MAX_VALUE;
		} else {
			lastValue = 0;
		}

		for(int x = 0 ; x < 3 ; x++) {
			for (int y = 0; y < 3; y++) {
				if ((x + y) % 2 == 1) {
					int val = environment.getDijkstraPos(environment.getRealPosX(getPosX() + x - 1), environment.getRealPosY(getPosY() + y - 1));
					if (val != -1 && ((val < lastValue && !environment.isPacmanInvinsible())
							|| val > lastValue && environment.isPacmanInvinsible())) {
						this.pasX = x - 1;
						this.pasY = y - 1;
						lastValue = val;
					}
				}
			}
		}

		environment.moveAgent(this, pasX, pasY);
	}

	/**
	 * R�cup�ration de l'avatar
	 * 
	 * @param moore les voisins
	 * @param x
	 * @param y
	 * @return l'avatar
	 */
	private AvatarAgent getAvatarHere(Agent[][] moore, int x, int y) {
		if (moore[x][y] instanceof AvatarAgent) {
			return (AvatarAgent) moore[x][y];
		}

		return null;
	}

	/**
	 * Retourne l'avatar qui se trouve � c�t�
	 * 
	 * @param moore les voisins
	 * @return l'avatar
	 */
	private AvatarAgent getAvatarAround(Agent[][] moore) {

		for(int x = 0 ; x < 3 ; x++) {
			for(int y = 0 ; y < 3 ; y++) {
				if((x + y) % 2 == 1) {
					AvatarAgent avatar = getAvatarHere(moore, x, y);
					if (avatar != null) {
						return avatar;
					}
				}
			}
		}

		return null;
	}

	@Override
	public void decide() {
		if (this.environment.isEndedGame()) {
    		return;
    	}
		
		nbTick++;
		if (nbTick % Config.getSpeedHunter() != 0) {
			return;
		}

		Agent[][] moore = environment.getMoore(this);

		if (!environment.isPacmanInvinsible()) {
			AvatarAgent avatar = getAvatarAround(moore);
			if (avatar != null) {
				avatar.kill();
			}
		}

		moveHunter();
	}

}
