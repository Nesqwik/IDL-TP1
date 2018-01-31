package pacman;

import core.agents.Agent;
import core.misc.Environment;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class AvatarAgent extends Agent implements KeyListener {
    final static int UP = 0;
    final static int DOWN = 1;
    final static int LEFT = 2;
    final static int RIGHT = 3;


    private int direction;

    public AvatarAgent(Environment environment, int posX, int posY) {
        super(environment, posX, posY);
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
        System.out.println(direction);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP:
                direction = UP;
                break;

            case KeyEvent.VK_DOWN:
                direction = DOWN;
                break;

            case KeyEvent.VK_LEFT:
                direction = LEFT;
                break;

            case KeyEvent.VK_RIGHT:
                direction = RIGHT;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
