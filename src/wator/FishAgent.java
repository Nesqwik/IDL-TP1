package wator;

import core.agents.Agent;
import core.misc.Config;
import core.misc.Environment;
import core.misc.Logger;

public class FishAgent extends SeaAgent {

    public FishAgent(Environment env, int posX, int posY, int breedTime) {
        super(env, posX, posY, breedTime);
    }

    @Override
    public int getShape() {
        return Agent.ROUND;
    }

    public void init(int x, int y, int breedTime) {
        super.init(x, y, breedTime);
        this.setColor(Config.yellow);
    }

    @Override
    public void newBorn(int x, int y) {
        //Logger.log("fishAgent;born");
        environment.addAgent(WatorFactory.newFish(environment, x, y));
    }

    public void kill() {
        //Logger.log("fishAgent;die");
        environment.removeAgent(this);
        //WatorFactory.addDiedFish(this);
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
