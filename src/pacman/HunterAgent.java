package pacman;

import core.agents.Agent;
import core.misc.Config;
import core.misc.Environment;

import java.awt.*;

public class HunterAgent extends Agent {
	private int pasX;
	private int pasY;
	private int nbTick = 0;
	private EnvironmentPacman environment;

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

	private void moveHunter() {
		this.pasX = 0;
		this.pasY = 0;

		if (!environment.isStartedGame()) {
			return;
		}
		
		int xMoinsUn = environment.isToric() ? (getPosX() - 1 + environment.getCols()) % environment.getCols() : getPosX() - 1;
		int xPlusUn = environment.isToric() ? (getPosX() + 1) % environment.getCols() : getPosX() + 1;
		int yMoinsUn = environment.isToric() ? (getPosY() - 1 + environment.getRows()) % environment.getRows() : getPosY() - 1;
		int yPlusUn = environment.isToric() ? (getPosY() + 1) % environment.getRows() : getPosY() + 1;

		int lastValue;

		if (!environment.isPacmanInvinsible()) {
			lastValue = Integer.MAX_VALUE;
		} else {
			lastValue = 0;
		}

		int val = environment.getDijkstraPos(xMoinsUn, getPosY());
		if (val != -1 && ((val < lastValue && !environment.isPacmanInvinsible())
				|| val > lastValue && environment.isPacmanInvinsible())) {
			this.pasX = -1;
			this.pasY = 0;
			lastValue = val;
		}

		val = environment.getDijkstraPos(xPlusUn, getPosY());
		if (val != -1 && ((val < lastValue && !environment.isPacmanInvinsible())
				|| val > lastValue && environment.isPacmanInvinsible())) {
			this.pasX = 1;
			this.pasY = 0;
			lastValue = val;
		}

		val = environment.getDijkstraPos(getPosX(), yMoinsUn);
		if (val != -1 && ((val < lastValue && !environment.isPacmanInvinsible())
				|| val > lastValue && environment.isPacmanInvinsible())) {
			this.pasX = 0;
			this.pasY = -1;
			lastValue = val;
		}

		val = environment.getDijkstraPos(getPosX(), yPlusUn);
		if (val != -1 && ((val < lastValue && !environment.isPacmanInvinsible())
				|| val > lastValue && environment.isPacmanInvinsible())) {
			this.pasX = 0;
			this.pasY = 1;
		}

		environment.moveAgent(this, pasX, pasY);
	}

	private AvatarAgent getAvatarHere(Agent[][] moore, int x, int y) {
		if (moore[x][y] instanceof AvatarAgent) {
			return (AvatarAgent) moore[x][y];
		}

		return null;
	}

	private AvatarAgent getAvatarAround(Agent[][] moore) {
		AvatarAgent avatar = getAvatarHere(moore, 0, 1);
		if (avatar != null) {
			return avatar;
		}

		avatar = getAvatarHere(moore, 2, 1);
		if (avatar != null) {
			return avatar;
		}

		avatar = getAvatarHere(moore, 1, 0);
		if (avatar != null) {
			return avatar;
		}

		avatar = getAvatarHere(moore, 1, 2);
		if (avatar != null) {
			return avatar;
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
