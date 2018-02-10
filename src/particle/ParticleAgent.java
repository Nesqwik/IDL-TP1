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
        this.setColor(Color.GRAY);
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
        this.setColor(Color.GRAY);
        this.setPasX(pasX);
        this.setPasY(pasY);
    }

    /**
     * Vérifie s'il y a une collision avec l'agent Frontière x + 1
     * 
     * @param moore les voisins
     * @return vrai si il y a collision avec un voisin
     */
    private boolean isCollideX(Agent[][] moore) {
        return moore[pasX + 1][1] instanceof FrontierAgent;
    }

    /**
     * Vérifie s'il y a une collision avec l'agent Frontière y + 0
     * 
     * @param moore les voisins
     * @return vrai si il y a une collision avec la frontière
     */
    private boolean isCollideY(Agent[][] moore) {
        return moore[1][pasY + 1] instanceof FrontierAgent;
    }

    
    /**
     * traitement lors d'une collision
     * 
     * @param otherAgent
     * @param moore voisins
     */
    protected void onCollide(ParticleAgent otherAgent, Agent[][] moore) {
        this.setColor(Color.RED);
        otherAgent.setColor(Color.RED);
        this.hasChanged();
    }

    @Override
    public void decide() {
        super.decide();
        Agent[][] moore = environment.getMoore(this);

        if (pasX == 0 && pasY == 0) {
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
