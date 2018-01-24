package wator;

import core.agents.Agent;
import core.misc.Environment;

public class FishAgent extends SeaAgent {

    public FishAgent(Environment env, int x, int y, int breedTime) {
        super(env, x, y, breedTime);
    }

    @Override
    protected void reproduct() {
        System.out.println("naissance");
        environment.addAgent(WatorFactory.newFish(environment, lastX, lastY));
    }

    @Override
    public void decide() {
        super.decide();

        Agent[][] moore = environment.getMoore(this);

        boolean hasMoved = moveIfCan(moore);
        if(hasMoved) {
            this.updateBreed();
        }
    }
}
