package pacman;

import core.agents.Agent;
import core.misc.Environment;

import java.awt.*;

public class HunterAgent extends Agent {
    private int pasX;
    private int pasY;
    private int speed = 0;
    private int nbTick = 0;

    public HunterAgent(Environment environment, int posX, int posY, int speed) {
        super(environment, posX, posY);
        this.speed = speed;
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
        int val = environment.getDijkstraPos(getPosX() - 1, getPosY());
        int lastValue = Integer.MAX_VALUE;
        if (val != -1 && val < lastValue) {
            this.pasX = -1;
            this.pasY = 0;
            lastValue = val;
        }

        val = environment.getDijkstraPos(getPosX() + 1, getPosY());
        if (val != -1 && val < lastValue) {
            this.pasX = 1;
            this.pasY = 0;
            lastValue = val;
        }

        val = environment.getDijkstraPos(getPosX(), getPosY() - 1);
        if (val != -1 && val < lastValue) {
            this.pasX = 0;
            this.pasY = -1;
            lastValue = val;
        }

        val = environment.getDijkstraPos(getPosX(), getPosY() + 1);
        if (val != -1 && val < lastValue) {
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
        nbTick++;
        if (nbTick % speed != 0) {
            return;
        }

        Agent[][] moore = environment.getMoore(this);

        AvatarAgent avatar = getAvatarAround(moore);
        if (avatar != null) {
            avatar.kill();
        }

        moveHunter();
    }


}
