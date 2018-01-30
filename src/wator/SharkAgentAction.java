package wator;

import core.agents.Agent;
import core.misc.Environment;
import core.misc.SMA;

public class SharkAgentAction extends SharkAgent {


    public SharkAgentAction(Environment env, Integer x, Integer y, Integer breedTime, Integer feedTime) {
        super(env, x, y, breedTime, feedTime);
    }

    @Override
    public void decide() {
        super.decide();
        Agent[][] moore = environment.getMoore(this);

        boolean hasEaten = eatIfCan(moore);
        if (!hasEaten) {
            boolean hasReproduct = reproductIfCan(moore);
            if (!hasReproduct) {
                moveIfCan(moore);
            }
        }

        if (feedTime == 0) {
            this.die();
        }
    }
}
