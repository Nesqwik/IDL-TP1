package wator;

import java.awt.Color;

import core.agents.Agent;
import core.misc.Config;
import core.misc.Environment;

/**
 * Agent poisson
 */
public class FishAgent extends SeaAgent {

    /**
     * Constructeur de l'agent poisson
     * 
     * @param env
     * @param posX
     * @param posY
     * @param breedTime
     */
    public FishAgent(Environment env, int posX, int posY, int breedTime) {
        super(env, posX, posY, breedTime);
    }

    @Override
    public int getShape() {
        return Agent.ROUND;
    }

    public void init(int x, int y, int breedTime) {
        super.init(x, y, breedTime);
    }

    @Override
    public Color getColor() {
        return super.getGradientColor(this.age, this.MAX_AGE, Config.green, Config.yellow);
    }

    @Override
    public void newBorn(int x, int y) {
        environment.addAgent(WatorFactory.newFish(environment, x, y));
    }

    /**
     * action lors de la suppression de l'agent poisson
     */
    public void kill() {
        environment.removeAgent(this);
    }

    @Override
    public void decide() {
        super.decide();
        this.setColor(Config.green);
    }

    @Override
    public void onDestroyed() {
        WatorFactory.addDiedFish(this);
    }
}
