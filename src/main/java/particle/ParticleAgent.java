package particle;

import core.agents.Agent;
import core.agents.FrontierAgent;
import core.misc.Environment;

import java.awt.*;

/**
 * Agent particule
 */
public class ParticleAgent extends Agent {

    /**
     * pas x
     */
    protected int pasX;
    /**
     * pas y
     */
    protected int pasY;

    private final int MAX_TIME = 1000;

    private int timeFromCollision = MAX_TIME;

    /**
     * Constructeur de l'agent particule
     * 
     * @param environment
     * @param posX
     * @param posY
     * @param pasX
     * @param pasY
     */
    public ParticleAgent(Environment environment, int posX, int posY, int pasX, int pasY) {
        super(environment, posX, posY);
        this.setPasX(pasX);
        this.setPasY(pasY);
    }

    @Override
    public int getShape() {
        return Agent.ROUND;
    }

    /**
     * initialise l'agent de particule
     * 
     * @param posX
     * @param posY
     * @param pasX
     * @param pasY
     */
    public void init(int posX, int posY, int pasX, int pasY) {
        super.init(posX, posY);
        this.setPasX(pasX);
        this.setPasY(pasY);
        timeFromCollision = MAX_TIME;
    }

    /**
     * V�rifie s'il y a une collision avec l'agent Fronti�re x + 1
     * 
     * @param moore les voisins
     * @return vrai si il y a collision avec un voisin
     */
    private boolean isCollideX(Agent[][] moore) {
        return moore[pasX + 1][1] instanceof FrontierAgent;
    }

    /**
     * V�rifie s'il y a une collision avec l'agent Fronti�re y + 0
     * 
     * @param moore les voisins
     * @return vrai si il y a une collision avec la fronti�re
     */
    private boolean isCollideY(Agent[][] moore) {
        return moore[1][pasY + 1] instanceof FrontierAgent;
    }

    @Override
    public Color getColor() {
        return this.getGradientColor(this.timeFromCollision, this.MAX_TIME, Color.GRAY, Color.RED);
    }
    
    /**
     * traitement lors d'une collision
     * 
     * @param otherAgent
     * @param moore voisins
     */
    protected void onCollide(ParticleAgent otherAgent, Agent[][] moore) {
        otherAgent.timeFromCollision = 0;
        timeFromCollision = 0;
        this.hasChanged();
    }

    @Override
    public void decide() {
        super.decide();
        Agent[][] moore = environment.getMoore(this);
        if (pasX == 0 && pasY == 0) {
            timeFromCollision = Math.min(timeFromCollision + 1, MAX_TIME);
            return;
        }

        if (moore[pasX + 1][pasY + 1] instanceof ParticleAgent) {
            ParticleAgent otherAgent = (ParticleAgent) moore[pasX + 1][pasY + 1];
            this.onCollide(otherAgent, moore);
        } else {
            if (this.isCollideX(moore)) {
                this.setPasX(-this.getPasX());
                this.hasChanged();
            }

            if (this.isCollideY(moore)) {
                this.setPasY(-this.getPasY());
                this.hasChanged();
            }

            if (moore[pasX + 1][pasY + 1] instanceof ParticleAgent) {
                ParticleAgent otherAgent = (ParticleAgent) moore[pasX + 1][pasY + 1];
                this.onCollide(otherAgent, moore);
            } else {
                this.environment.moveAgent(this, pasX, pasY);
            }
        }

        timeFromCollision = Math.min(timeFromCollision + 1, MAX_TIME);
        logAgent();
    }


    /**
     * getter pasX
     * 
     * @return pasX
     */
    public int getPasX() {
        return pasX;
    }

    /**
     * setter pas X
     * 
     * @param pasX
     */
    public void setPasX(int pasX) {
        this.pasX = pasX;
    }

    /**
     * getter pasY
     * 
     * @return pasY
     */
    public int getPasY() {
        return pasY;
    }

    /**
     * setter pasY
     * 
     * @param pasY
     */
    public void setPasY(int pasY) {
        this.pasY = pasY;
    }

    @Override
    public String toString() {
        return "ParticleAgent;" + this.hashCode() + ";" + this.getPosX() + ";" + this.getPosY() + ";" + this.getPasX() + ";" + this.getPasY();
    }

    @Override
    public void onDestroyed() {

    }


}
