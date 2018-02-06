package pacman;

import core.agents.Agent;
import core.misc.Environment;
import core.misc.SMA;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class AvatarAgent extends Agent implements KeyListener {
    final static int UP = 0;
    final static int DOWN = 1;
    final static int LEFT = 2;
    final static int RIGHT = 3;

    private int speed;
    private int tickNumber = 0;

    private int pasX;
    private int pasY;

    public AvatarAgent(Environment environment, int posX, int posY, int speed) {
        super(environment, posX, posY);
        this.speed = speed;
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
        tickNumber++;
        if (tickNumber % speed != 0) {
            return;
        }

        Agent[][] moore = environment.getMoore(this);

        if (moore[pasX + 1][pasY + 1] == null) {
            environment.moveAgent(this, pasX, pasY);
            environment.dijkstra(this);
        }
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
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    public void kill() {
        this.setAlive(false);
        this.environment.removeAgent(this);
    }
}
